package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.User;
import com.store.kafka.KafkaDispatcher;
import com.store.repository.UserRepository;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SendMessageToAllUsersParser implements ConsumerFunction<String> {

  private final UserRepository repository = new UserRepository();
  private final KafkaDispatcher<User> dispatcher = new KafkaDispatcher<>();

  public SendMessageToAllUsersParser() throws SQLException {
  }

  @Override
  public void parse(ConsumerRecord<String, String> record)
      throws ExecutionException, InterruptedException, SQLException {
    System.out.println("Sending messages to all users");
    for (User user : repository.getAll()) {
      System.out.println("Sending message to " + user.getId());
      dispatcher.send(record.value(), user.getId(), user);
    }
    System.out.println("The messages has been sent");
  }
}
