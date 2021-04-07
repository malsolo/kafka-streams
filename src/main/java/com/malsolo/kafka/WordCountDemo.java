package com.malsolo.kafka;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

/**
 * Basic Kafka Streams demo application: word count.
 *
 * See https://docs.confluent.io/platform/current/streams/developer-guide/write-streams.html
 * See https://github.com/confluentinc/kafka-streams-examples/blob/6.1.1-post/src/main/java/io/confluent/examples/streams/WordCountLambdaExample.java
 * See https://kafka.apache.org/27/documentation/streams/quickstart
 * See https://github.com/apache/kafka/blob/2.7/streams/examples/src/main/java/org/apache/kafka/streams/examples/wordcount/WordCountDemo.java
 *
 * <p>
 * HOW TO RUN THIS EXAMPLE:
 *
 * <p>
 * 1) Start <a href='http://docs.confluent.io/current/quickstart.html#quickstart'>Kafka</a>.
 *
 * <p>
 * 2) Create the input and output topics used by this example.
 * <pre>
 * {@code
 * $ bin/kafka-topics --bootstrap-server localhost:9092 --create --topic streams-plaintext-input \
 *                   --partitions 1 --replication-factor 1
 * $ bin/kafka-topics --bootstrap-server localhost:9092 --create --topic streams-wordcount-output \
 *                   --partitions 1 --replication-factor 1
 * }</pre>
 *
 * <p>
 * 3) Start this example application
 *
 * <p>
 * 4) Write some input data to the source topic
 * <pre>
 * {@code
 * # Start the console producer. You can then enter input data by writing some line of text, followed by ENTER:
 * #
 * #   hello kafka streams<ENTER>
 * #   all streams lead to kafka<ENTER>
 * #   join kafka summit<ENTER>
 * #
 * # Every line you enter will become the value of a single Kafka message.
 * $ bin/kafka-console-producer --broker-list localhost:9092 --topic streams-plaintext-input
 * }</pre>
 * 
 * <p>
 * 5) Inspect the resulting data in the output topic, e.g. via {@code kafka-console-consumer}.
 * <pre>
 * {@code
 * $ bin/kafka-console-consumer --topic streams-wordcount-output --from-beginning \
 *                              --bootstrap-server localhost:9092 \
 *                              --property print.key=true \
 *                              --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
 * }</pre>
 * <p>
 * You should see output data similar to:
 * <pre>
 * {@code
 * hello    1
 * kafka    1
 * streams  1
 * all      1
 * streams  2
 * lead     1
 * to       1
 * join     1
 * kafka    3
 * summit   1
 * }</pre>
 *
 */
public class WordCountDemo {

    public static final String INPUT_TOPIC = "streams-plaintext-input";
    public static final String OUTPUT_TOPIC = "streams-wordcount-output";

    public static void main(String[] args) {
        WordCountDemo demo = new WordCountDemo();

        Properties props = demo.createStreamsConfiguration();
        Topology topology = demo.createTopology();

        KafkaStreams kafkaStreams = new KafkaStreams(topology, props);

        kafkaStreams.start();

        System.out.println(topology.describe());

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    public Properties createStreamsConfiguration() {
        var props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-demo");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return props;
    }

    public Topology createTopology() {
        Serde<Long> longSerde = Serdes.Long();
        Serde<String> stringSerde = Serdes.String();

        var builder = new StreamsBuilder();

        KStream<String, String> lines = builder.stream(INPUT_TOPIC, Consumed.with(stringSerde, stringSerde));

        KTable<String, Long> counts = lines
            .flatMapValues(line -> Arrays.asList(line.toLowerCase().split("\\W+")))
            .groupBy((key, word) -> word)
            .count();

        counts.toStream().to(OUTPUT_TOPIC, Produced.with(stringSerde, longSerde));

        return builder.build();
    }
}
