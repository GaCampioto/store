package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Order;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class NewOrderParser implements ConsumerFunction<Order> {

  @Override
  public void parse(ConsumerRecord<String, Order> record) {
    System.out
        .println("----------------------------------------------------------------------");
    System.out.println("received new order");
    System.out.println(
        "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
            .offset() + " | partition: " + record.partition());
    System.out
        .println("----------------------------------------------------------------------");
  }
}
