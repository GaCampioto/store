package com.store.producer;

import com.store.kafka.KafkaDispatcher;
import com.store.producer.domain.Email;
import com.store.producer.domain.Order;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainProducer {

  public static final String STORE_NEW_ORDER_TOPIC = "store.new-order";
  public static final String SEND_EMAIL_TOPIC = "store.send-email";

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    sendNewOrders();
    sendEmails();
  }

  private static void sendNewOrders() throws InterruptedException, ExecutionException {
    try (var dispatcher = new KafkaDispatcher<Order>()) {
      for (int ignored = 0; ignored < 100; ignored++) {
        int orderNumber = new Random().nextInt(999);
        Order order = new Order(orderNumber,
            BigDecimal.valueOf(new Random().nextLong()));

        dispatcher.send(STORE_NEW_ORDER_TOPIC, String.valueOf(orderNumber), order);
      }
    }
  }

  private static void sendEmails() throws ExecutionException, InterruptedException {
    try (var dispatcher = new KafkaDispatcher<Email>()) {
      for (int ignored = 0; ignored < 100; ignored++) {
        int orderNumber = new Random().nextInt(999);
        Email email = new Email("new order",
            "Your order " + orderNumber + " is being processed");

        dispatcher.send(SEND_EMAIL_TOPIC, String.valueOf(orderNumber), email);
      }
    }
  }
}
