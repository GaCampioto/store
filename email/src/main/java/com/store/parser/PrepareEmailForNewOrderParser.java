package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Email;
import com.store.domain.Order;
import com.store.kafka.KafkaDispatcher;
import com.store.model.Message;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class PrepareEmailForNewOrderParser implements ConsumerFunction<Order> {

  private final String SERVICE_NAME = "email";
  public static final String SEND_EMAIL_TOPIC = "store.send-email";
  private final KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>();

  @Override
  public void parse(ConsumerRecord<String, Message<Order>> record)
      throws ExecutionException, InterruptedException {
    Message<Order> message = record.value();
    Order order = message.getPayload();

    Email email = new Email(order.getUserEmail(), "new order",
        "Your order " + order.getOrderNumber() + " is being processed");
    emailDispatcher.send(SEND_EMAIL_TOPIC, order.getUserEmail(), email, message.getId().append(SERVICE_NAME));
  }
}
