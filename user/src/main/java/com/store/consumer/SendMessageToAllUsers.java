package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.SendMessageToAllUsersParser;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SendMessageToAllUsers {
  public static final List<String> TOPICS = Collections.singletonList("store.send-message-to-all-users");

  public static void main(String[] args)
      throws SQLException, ExecutionException, InterruptedException {
    try (var kafkaReceiver = new KafkaReceiver<>(
        SendMessageToAllUsers.class.getSimpleName(),
        TOPICS,
        Map.of(),
        new SendMessageToAllUsersParser())) {
      kafkaReceiver.run();
    }
  }
}
