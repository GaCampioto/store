package com.store;

import com.store.domain.Order;
import com.store.kafka.KafkaDispatcher;
import com.store.domain.Email;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainProducer {

  public static final String STORE_NEW_ORDER_TOPIC = "store.new-order";
  public static final String SEND_EMAIL_TOPIC = "store.send-email";

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    String userEmail = new Random().nextInt(999) + "@email.com";
    try (var orderDispatcher = new KafkaDispatcher<Order>()) {
      try (var emailDispatcher = new KafkaDispatcher<Email>()) {
        for (int ignored = 0; ignored < 100; ignored++) {
          int orderNumber = new Random().nextInt(999);
          Order order = new Order(userEmail, orderNumber,
              BigDecimal.valueOf(new Random().nextLong()));

          orderDispatcher.send(STORE_NEW_ORDER_TOPIC, userEmail, order);

          Email email = new Email(userEmail, "new order",
              "Your order " + orderNumber + " is being processed");
          emailDispatcher.send(SEND_EMAIL_TOPIC, userEmail, email);
        }

      }
    }
  }
}
