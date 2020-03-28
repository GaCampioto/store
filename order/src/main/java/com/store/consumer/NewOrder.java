package com.store.consumer;

import com.store.domain.Order;
import com.store.kafka.KafkaReceiver;
import com.store.parser.NewOrderParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NewOrder {

  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args) {
    NewOrderParser newOrderParser = new NewOrderParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        Order.class,
        NewOrder.class.getSimpleName(),
        TOPICS,
        Map.of(),
        newOrderParser)) {
      kafkaReceiver.run();
    }
  }
}
