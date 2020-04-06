package com.store;

import com.store.domain.Order;
import com.store.kafka.KafkaDispatcher;
import com.store.model.CorrelationId;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainProducer {

  public static final String STORE_NEW_ORDER_TOPIC = "store.new-order";
  public static final String REPORT_FOR_USER = "store.create-report-for-user";
  public static final String SEND_MESSAGE_TO_ALL_USER_TOPIC = "store.send-message-to-all-users";
  public static final String SERVICE_NAME = "main-producer";

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    try (var orderDispatcher = new KafkaDispatcher<Order>()) {
      for (int ignored = 0; ignored < 10; ignored++) {
        String userEmail = new Random().nextInt(999) + "@email.com";
        int orderNumber = new Random().nextInt(999);
        Order order = new Order(userEmail, orderNumber,
            BigDecimal.valueOf(new Random().nextLong()));

        orderDispatcher
            .send(STORE_NEW_ORDER_TOPIC, userEmail, order, new CorrelationId(SERVICE_NAME));
      }
    }

    try (var batchDispatcher = new KafkaDispatcher<String>()) {
      batchDispatcher.send(SEND_MESSAGE_TO_ALL_USER_TOPIC, REPORT_FOR_USER, REPORT_FOR_USER,
          new CorrelationId(SERVICE_NAME));
    }
  }
}
