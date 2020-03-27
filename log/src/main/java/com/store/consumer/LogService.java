package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

  public static final Pattern ALL_STORE = Pattern.compile("store.*");

  public static void main(String[] args) {
    try (var kafkaReceiver = new KafkaReceiver<>(
        String.class,
        LogService.class.getSimpleName(),
        ALL_STORE,
        Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()))) {
      kafkaReceiver.run(record -> {
        System.out
            .println("----------------------------------------------------------------------");
        System.out.println("received log");
        System.out.println(
            "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
                .offset() + " | partition: " + record.partition());
        System.out
            .println("----------------------------------------------------------------------");
      });
    }
  }
}
