package com.malsolo.kafka.purchase.clients;

import static com.malsolo.kafka.purchase.clients.Generator.generatePurchase;
import static com.malsolo.kafka.purchase.config.TopicsConfig.TRANSACTIONS_TOPIC_SOURCE;

import com.malsolo.kafka.purchase.model.avro.Purchase;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
public class Producer {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";
    public static final int NUMBER_OF_PURCHASES = 10;

    public static void main(String[] args) {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);

        try (var producer = new KafkaProducer<String, Purchase>(properties)) {
            log.info("About to produce purchases...");

            for (var i = 0L; i < NUMBER_OF_PURCHASES; i++) {
                Purchase purchase = generatePurchase();

                log.info("Purchase created {}", purchase);

                var purchaseRecord = new ProducerRecord<String, Purchase>(TRANSACTIONS_TOPIC_SOURCE, null, purchase);
                producer.send(purchaseRecord, ((metadata, exception) -> {
                    if (exception == null) {
                        log.info("Purchase record sent successfully, to topic {}, partition {}, and offset {}",
                            metadata.topic(), metadata.partition(), metadata.offset());
                    } else {
                        log.error("Purchase Sent failed: {}", exception.getMessage());
                        exception.printStackTrace();
                    }
                }));

                log.info("Purchase produced {}.", purchaseRecord);
            }
        }
    }

}
