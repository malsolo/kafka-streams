package com.malsolo.kafka.purchase;

import static com.malsolo.kafka.purchase.config.TopicsConfig.APPLICATION_ID;
import static com.malsolo.kafka.purchase.config.TopicsConfig.BOOTSTRAP_SERVERS;
import static com.malsolo.kafka.purchase.config.TopicsConfig.CORRELATED_PURCHASES_TOPIC_SINK;
import static com.malsolo.kafka.purchase.config.TopicsConfig.EMPLOYEE_ID;
import static com.malsolo.kafka.purchase.config.TopicsConfig.PATTERNS_TOPIC_SINK;
import static com.malsolo.kafka.purchase.config.TopicsConfig.PURCHASES_KEYED_TOPIC_SINK;
import static com.malsolo.kafka.purchase.config.TopicsConfig.PURCHASES_TOPIC_SINK;
import static com.malsolo.kafka.purchase.config.TopicsConfig.REWARDS_TOPIC_SINK;
import static com.malsolo.kafka.purchase.config.TopicsConfig.SCHEMA_REGISTRY_URL;
import static com.malsolo.kafka.purchase.config.TopicsConfig.TRANSACTIONS_TOPIC_SOURCE;
import static com.malsolo.kafka.purchase.model.ModelHelper.localDateToEpochMillis;

import com.malsolo.kafka.purchase.joiner.PurchaseJoiner;
import com.malsolo.kafka.purchase.model.ModelHelper;
import com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase;
import com.malsolo.kafka.purchase.model.avro.Purchase;
import com.malsolo.kafka.purchase.model.avro.PurchasePattern;
import com.malsolo.kafka.purchase.model.avro.RewardAccumulator;
import com.malsolo.kafka.purchase.repository.PurchaseRepositorySysOut;
import com.malsolo.kafka.purchase.transformer.PurchaseRewardTransformer;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;

public class PurchaseTopologyApp {

    public static void main(String[] args) {
        var app = new PurchaseTopologyApp();

        var props = app.createStreamsConfiguration();
        var topology = app.createTopology();

        var streams = new KafkaStreams(topology, props);

        System.out.println(topology.describe());

        var latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            latch.countDown();
        }));

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    public Properties createStreamsConfiguration() {
        var props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);

        return props;
    }

    public Topology createTopology() {
        var builder = new StreamsBuilder();

        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var integerSerde = Serdes.Integer();
        var serdeConfig = Collections.singletonMap("schema.registry.url", SCHEMA_REGISTRY_URL);
        final Serde<Purchase> valuePurchaseSpecificAvroSerde = new SpecificAvroSerde<>();
        valuePurchaseSpecificAvroSerde.configure(serdeConfig, false); // `false` for record values

        //0th TYPED STREAM
        KStream<String, Purchase> stream = builder.stream(TRANSACTIONS_TOPIC_SOURCE);

        //1st PROCESSOR: MASKING
        var maskedPurchaseKStream = stream
            .mapValues(ModelHelper::purchaseMaskCreditCard);

        //FILTERING PURCHASES
        KeyValueMapper<String, Purchase, Long> purchaseDateAsKey = (key, purchase) ->
            localDateToEpochMillis(purchase.getPurchaseDate());

        //Generating a Key
        KStream<Long, Purchase> filteredKStream = maskedPurchaseKStream
            .filter((key, purchase) -> purchase.getPrice() > 5.00).selectKey(purchaseDateAsKey);

        filteredKStream.print(Printed.<Long, Purchase>toSysOut().withLabel("purchases"));

        filteredKStream.to(PURCHASES_TOPIC_SINK,
            Produced.with(longSerde, valuePurchaseSpecificAvroSerde));

        //2nd PROCESSOR: PATTERNS
        var patternKStream = maskedPurchaseKStream
            .mapValues(ModelHelper::purchasePatternfromPurchase);

        patternKStream.print(Printed.<String, PurchasePattern>toSysOut().withLabel("patterns"));

        patternKStream.to(PATTERNS_TOPIC_SINK);

        //3rd PROCESSOR: REWARDS
        //State store for calculating the rewards.
        //var rewardsStreamPartitioner = new RewardsStreamPartitioner();

        var maskedPurchaseKeyedKStream = maskedPurchaseKStream
            .selectKey((key, purchase) -> purchase.getCustomerId())
            .peek((key, purchase) -> System.out
                .printf("Purchases to rewards, key selected %s for %s \n", key, purchase));

        maskedPurchaseKeyedKStream.print(Printed.<String, Purchase>toSysOut().withLabel("purchases-keyed"));

        maskedPurchaseKeyedKStream.to(PURCHASES_KEYED_TOPIC_SINK);

        /*
        var transByCustomerStream = maskedPurchaseKStream
            .repartition(Repartitioned.streamPartitioner(rewardsStreamPartitioner)
            .withName(CUSTOMER_TRANSACTIONS_TOPIC)
        );
         */
        KStream<String, Purchase> transByCustomerStream = builder.stream(PURCHASES_KEYED_TOPIC_SINK);

        //Adding a state store
        var rewardsStateStoreName = "rewardsPointsStore";
        var storeSupplier = Stores.inMemoryKeyValueStore(rewardsStateStoreName);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, integerSerde);
        builder.addStateStore(storeBuilder);

        var statefulRewardAccumulator = transByCustomerStream
            .transformValues(() -> new PurchaseRewardTransformer(rewardsStateStoreName), rewardsStateStoreName);

        statefulRewardAccumulator.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));

        statefulRewardAccumulator.to(REWARDS_TOPIC_SINK);

        //4th PROCESSOR: BRANCH
        @SuppressWarnings("unchecked")
        KStream<String, Purchase>[] kstreamByDept = maskedPurchaseKStream
            .selectKey((k, v) -> v.getCustomerId())
            .branch(this.isAmusement(), this.isElectronics());

        var amusementIndex = 0;
        var electronicsIndex = 1;

        var amusementStream = kstreamByDept[amusementIndex];
        amusementStream.print(Printed.<String, Purchase>toSysOut().withLabel("amusementStream"));

        var electronicsStream = kstreamByDept[electronicsIndex];
        electronicsStream.print(Printed.<String, Purchase>toSysOut().withLabel("electronicsStream"));

        //5th PROCESSOR: JOIN
        var purchaseJoiner = new PurchaseJoiner();

        var twentyMinuteWindow = JoinWindows.of(Duration.ofMinutes(20));

        var joinedStream = amusementStream.join(electronicsStream,
            purchaseJoiner,
            twentyMinuteWindow);

        joinedStream.print(Printed.<String, CorrelatedPurchase>toSysOut().withLabel("joinedStream"));

        joinedStream.to(CORRELATED_PURCHASES_TOPIC_SINK);

        // ACTION -> Repository
        var purchaseRepository = new PurchaseRepositorySysOut();

        ForeachAction<String, Purchase> purchaseForeachAction = (key, purchase) ->
            purchaseRepository.save(purchase);

        maskedPurchaseKStream
            .filter((key, purchase) -> EMPLOYEE_ID.equals(purchase.getEmployeeId()))
            .foreach(purchaseForeachAction);

        return builder.build();
    }

    private Predicate<String, Purchase> isAmusement() {
        return (key, purchase) -> {
            boolean is;
            if (purchase == null || purchase.getDepartment() == null) {
                is = false;
            } else {
                is = purchase.getDepartment().equalsIgnoreCase("books") ||
                    purchase.getDepartment().equalsIgnoreCase("movies") ||
                    purchase.getDepartment().equalsIgnoreCase("music") ||
                    purchase.getDepartment().equalsIgnoreCase("games") ||
                    purchase.getDepartment().equalsIgnoreCase("toys");
            }
            return is;
        };
    }

    private Predicate<String, Purchase> isElectronics() {
        return (key, purchase) -> {
            boolean is;
            if (purchase == null || purchase.getDepartment() == null) {
                is = false;
            } else {
                is = purchase.getDepartment().equalsIgnoreCase("electronics") ||
                    purchase.getDepartment().equalsIgnoreCase("computers");
            }
            return is;
        };
    }
}
