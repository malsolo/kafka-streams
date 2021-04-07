package com.malsolo.kafka.purchase;

import static com.malsolo.kafka.purchase.config.TopicsConfig.BOOTSTRAP_SERVERS;
import static com.malsolo.kafka.purchase.config.TopicsConfig.PURCHASE_APPLICATION_ID;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class PurchaseTopologyApp {

    public static void main(String[] args) {
        var app = new PurchaseTopologyApp();

        var props = app.createStreamsConfiguration();
        var topology = app.createTopology();

        var streams = new KafkaStreams(topology, props);

        streams.start();

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

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, PURCHASE_APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        return props;
    }

    public Topology createTopology() {
        return null;
    }

}
