package com.store.kafka;

import com.store.gson.GSONSerializer;
import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {

  private final KafkaProducer<String, T> producer;

  public KafkaDispatcher() {
    this.producer = new KafkaProducer<>(properties());
  }

  public void send(String topic, String key, T value)
      throws InterruptedException, ExecutionException {
    producer.send(new ProducerRecord<>(topic, key, value),
        callback()).get();
  }

  private Properties properties() {
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties
        .setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        GSONSerializer.class.getName());
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
