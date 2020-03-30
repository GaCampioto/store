package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Order;
import com.store.repository.UserRepository;
import java.sql.SQLException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CreateUserParser implements ConsumerFunction<Order> {

  private final UserRepository repository = new UserRepository();

  public CreateUserParser() throws SQLException {
  }

  @Override
  public void parse(ConsumerRecord<String, Order> record) throws SQLException {
    Order order = record.value();
    if(!repository.exists(order.getUserEmail())) {
      repository.createUser(order.getUserEmail());
    }

    System.out
        .println("----------------------------------------------------------------------");
    System.out.println("received new order for user");
    System.out.println(
        "topic: " + record.topic() + " | value: " + order + " | offset: " + record
            .offset() + " | partition: " + record.partition());
    System.out
        .println("----------------------------------------------------------------------");
  }
}
