package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Order;
import com.store.kafka.KafkaDispatcher;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class NewOrderParser implements ConsumerFunction<Order> {

  private final String FREE_SHIPPING_TOPIC = "store.free-shipping";
  private final String NORMAL_SHIPPING_TOPIC = "store.normal-shipping";
  private final KafkaDispatcher<Order> dispatcher = new KafkaDispatcher<>();

  @Override
  public void parse(ConsumerRecord<String, Order> record)
      throws ExecutionException, InterruptedException {
    System.out
        .println("----------------------------------------------------------------------");
    Order order = record.value();
    if(order.isFreeShipping()) {
      System.out.println("received new order with free shipping");
      dispatcher.send(FREE_SHIPPING_TOPIC, String.valueOf(order.getOrderNumber()), order);
    } else {
      System.out.println("received new order with normal shipping");
      dispatcher.send(NORMAL_SHIPPING_TOPIC, String.valueOf(order.getOrderNumber()), order);
    }
    System.out.println(
        "topic: " + record.topic() + " | value: " + order + " | offset: " + record
            .offset() + " | partition: " + record.partition());
    System.out
        .println("----------------------------------------------------------------------");
  }
}