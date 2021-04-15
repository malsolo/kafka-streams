# Description

TODO

# How to run it

## Start kafka

Start
```
$ docker-compose up -d
```

Verify

```
$ docker-compose ps
```

Check

```
$ docker-compose logs broker -f
```

## Topics management

```
$ docker exec -it broker kafka-topics --list --bootstrap-server localhost:9092

$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-transactions
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-purchases
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-purchases-keyed
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-patterns
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-rewards
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-correlated-purchases
$ docker exec -it broker kafka-topics --delete --bootstrap-server localhost:9092 --topic kafka-streams-demo-customer-transactions

$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafka-streams-demo-transactions
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafka-streams-demo-purchases
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafka-streams-demo-purchases-keyed
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic kafka-streams-demo-patterns
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic kafka-streams-demo-rewards
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafka-streams-demo-correlated-purchases
$ docker exec -it broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafka-streams-demo-customer-transactions

$ docker exec -it broker kafka-topics --list --bootstrap-server localhost:9092 | grep kafka-streams-demo-

```

## Run the application

Run **PurchaseTopologyApp**

## Produce and consume messages

### Consume messages

```
$ docker exec -it broker kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic kafka-streams-demo-transactions --property schema.registry.url=http://localhost:8081 --from-beginning | jq .

$ docker exec -it broker kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic kafka-streams-demo-purchases --property schema.registry.url=http://localhost:8081 --from-beginning | jq .

$ docker exec -it broker kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic kafka-streams-demo-purchases-keyed --property schema.registry.url=http://localhost:8081 --from-beginning | jq .

$ docker exec -it broker kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic kafka-streams-demo-patterns --property schema.registry.url=http://localhost:8081 --from-beginning | jq .

$ docker exec -it broker kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic kafka-streams-demo-rewards --property schema.registry.url=http://localhost:8081 --from-beginning | jq .
```