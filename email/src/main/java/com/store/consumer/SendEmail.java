package com.store.consumer;

import com.store.domain.Email;
import com.store.kafka.KafkaReceiver;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SendEmail {

  public static final List<String> TOPICS = Collections.singletonList("store.send-email");

  public static void main(String[] args) {
    try (var kafkaReceiver = new KafkaReceiver<>(
        Email.class,
        SendEmail.class.getSimpleName(),
        TOPICS,
        Map.of())) {
      kafkaReceiver.run(record -> {
        System.out
            .println("----------------------------------------------------------------------");
        System.out.println("received email request");
        System.out.println(
            "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
                .offset() + " | partition: " + record.partition());
        System.out.println("----------------------------------------------------------------------");
      });
    }
  }
}
