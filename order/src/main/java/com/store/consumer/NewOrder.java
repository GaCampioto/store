package com.store.consumer;

import com.store.domain.Order;
import com.store.kafka.KafkaReceiver;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NewOrder {

  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args) {
    try (var kafkaReceiver = new KafkaReceiver<>(
        Order.class,
        NewOrder.class.getSimpleName(),
        TOPICS,
        Map.of())) {
      kafkaReceiver.run(record -> {
        System.out
            .println("----------------------------------------------------------------------");
        System.out.println("received new order");
        System.out.println(
            "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
                .offset() + " | partition: " + record.partition());
        System.out
            .println("----------------------------------------------------------------------");
      });
    }
  }
}
