package com.store.kafka;

import com.store.gson.GSONSerializer;
import com.store.model.CorrelationId;
import com.store.model.Message;
import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {

  private final KafkaProducer<String, Message<T>> producer;

  public KafkaDispatcher() {
    this.producer = new KafkaProducer<>(properties());
  }

  public void send(String topic, String key, T payload, CorrelationId id)
      throws InterruptedException, ExecutionException {
    sendAsync(topic, key, payload, id).get();
  }

  public Future sendAsync(String topic, String key, T payload, CorrelationId id) {
    return producer.send(new ProducerRecord<>(topic, key, new Message(id, payload)),
        callback());
  }

  private Properties properties() {
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties
        .setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        GSONSerializer.class.getName());
    properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
    properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1");
    return properties;
  }

  private Callback callback() {
    return (data, exception) -> {
      System.out.println("------------");
      System.out.println("sent record");
      System.out.println(
          "topic: " + data.topic() + " | timestamp: " + data.timestamp() + " | offset: " + data
              .offset());
      System.out.println("---------------");
    };
  }

  @Override
  public void close() {
    producer.close();
  }
}
