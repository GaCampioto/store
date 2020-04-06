package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.PrepareEmailForNewOrderParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PrepareEmailForNewOrder {

  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    try (var kafkaReceiver = new KafkaReceiver<>(
        PrepareEmailForNewOrder.class.getSimpleName(),
        TOPICS,
        Map.of(),
        new PrepareEmailForNewOrderParser())) {
      kafkaReceiver.run();
    }
  }
}
