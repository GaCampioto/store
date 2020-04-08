package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Order;
import com.store.kafka.KafkaDispatcher;
import com.store.model.Message;
import com.store.repository.OrderRepository;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class NewOrderParser implements ConsumerFunction<Order> {

  private final String FREE_SHIPPING_TOPIC = "store.free-shipping";
  private final String NORMAL_SHIPPING_TOPIC = "store.normal-shipping";
  private final String SERVICE_NAME = "order";

  private final KafkaDispatcher<Order> dispatcher = new KafkaDispatcher<>();
  private final OrderRepository orderRepository = new OrderRepository();

  public NewOrderParser() throws SQLException {
  }

  @Override
  public void parse(ConsumerRecord<String, Message<Order>> record)
      throws ExecutionException, InterruptedException, SQLException {
    Message<Order> message = record.value();
    Order order = message.getPayload();
    if (alreadyProcessed(order)) {
      return;
    }

    if(order.isFreeShipping()) {
      System.out.println("received new order with free shipping");
      orderRepository.createOrder(order);
      dispatcher.send(FREE_SHIPPING_TOPIC, order.getUserEmail(), order, message.getId().append(SERVICE_NAME));
    } else {
      System.out.println("received new order with normal shipping");
      dispatcher.send(NORMAL_SHIPPING_TOPIC, order.getUserEmail(), order, message.getId().append(SERVICE_NAME));
    }
  }

  private boolean alreadyProcessed(Order order) throws SQLException {
    return orderRepository.exists(order);
  }
}
