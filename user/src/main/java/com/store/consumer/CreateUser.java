package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.CreateUserParser;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateUser {
  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args)
      throws SQLException, ExecutionException, InterruptedException {
    CreateUserParser createUserParser = new CreateUserParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        CreateUser.class.getSimpleName(),
        TOPICS,
        Map.of(),
        createUserParser)) {
      kafkaReceiver.run();
    }
  }
}
