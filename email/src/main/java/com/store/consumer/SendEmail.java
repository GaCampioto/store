package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.SendEmailParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SendEmail {

  public static final List<String> TOPICS = Collections.singletonList("store.send-email");

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    SendEmailParser sendEmailParser = new SendEmailParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        SendEmail.class.getSimpleName(),
        TOPICS,
        Map.of(),
        sendEmailParser)) {
      kafkaReceiver.run();
    }
  }
}
