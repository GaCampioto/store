package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.NewOrderParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NewOrder {

  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    NewOrderParser newOrderParser = new NewOrderParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        NewOrder.class.getSimpleName(),
        TOPICS,
        Map.of(),
        newOrderParser)) {
      kafkaReceiver.run();
    }
  }
}
