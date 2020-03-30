package com.store.consumer;

import com.store.domain.Order;
import com.store.kafka.KafkaReceiver;
import com.store.parser.CreateUserParser;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateUser {
  public static final List<String> TOPICS = Collections.singletonList("store.new-order");

  public static void main(String[] args) throws SQLException {
    CreateUserParser createUserParser = new CreateUserParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        Order.class,
        CreateUser.class.getSimpleName(),
        TOPICS,
        Map.of(),
        createUserParser)) {
      kafkaReceiver.run();
    }
  }
}
