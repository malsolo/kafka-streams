package com.malsolo.kafka.purchase.config;

public class TopicsConfig {
    public static final String APPLICATION_ID = "kafka-streams-demo";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    public static final String TRANSACTIONS_TOPIC_SOURCE = "kafka-streams-demo-transactions";
    public static final String PURCHASES_TOPIC_SINK = "kafka-streams-demo-purchases";
    public static final String PATTERNS_TOPIC_SINK = "kafka-streams-demo-patterns";
    public static final String CUSTOMER_TRANSACTIONS_TOPIC = "kafka-streams-demo-customer-transactions";
    public static final String REWARDS_TOPIC_SINK = "kafka-streams-demo-rewards";
    public static final String CORRELATED_PURCHASES_TOPIC_SINK = "kafka-streams-demo-correlated-purchases";

    public static final String EMPLOYEE_ID = "000000";

}
