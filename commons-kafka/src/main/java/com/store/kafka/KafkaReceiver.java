package com.store.kafka;

import com.store.ConsumerFunction;
import com.store.gson.GSONDeserializer;
import com.store.gson.GSONSerializer;
import com.store.model.Message;
import java.io.Closeable;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaReceiver<T> implements Closeable {

  public static final int TIMEOUT_IN_MILLIS = 3000;
  public static final String DLQ = "DLQ_";

  private final KafkaConsumer<String, Message<T>> consumer;
  private final ConsumerFunction<T> consumerFunction;

  public KafkaReceiver(String groupId, Collection<String> topics,
      Map<String, String> overrideProperties, ConsumerFunction<T> consumerFunction) {
    this(groupId, overrideProperties, consumerFunction);
    this.consumer.subscribe(topics);
  }

  public KafkaReceiver(String groupId, Pattern pattern,
      Map<String, String> overrideProperties, ConsumerFunction<T> consumerFunction) {
    this(groupId, overrideProperties, consumerFunction);
    this.consumer.subscribe(pattern);
  }

  private KafkaReceiver(String groupId, Map<String, String> overrideProperties,
      ConsumerFunction<T> consumerFunction) {
    this.consumer = new KafkaConsumer<>(getProperties(groupId, overrideProperties));
    this.consumerFunction = consumerFunction;
  }

  public void run() throws ExecutionException, InterruptedException {
    try (var dlqDispatcher = new KafkaDispatcher<>()) {
      while (true) {
        var records = consumer.poll(Duration.ofMillis(TIMEOUT_IN_MILLIS));
        if (!records.isEmpty()) {
          for (var record : records) {
            try {
              System.out
                  .println(
                      "----------------------------------------------------------------------");
              System.out.println(
                  "topic: " + record.topic() + " | offset: " + record.offset() + " | partition: "
                      + record.partition());
              consumerFunction.parse(record);
              System.out
                  .println(
                      "----------------------------------------------------------------------");
            } catch (Exception e) {
              e.printStackTrace();
              String dlqTopic = DLQ + record.topic();
              dlqDispatcher.send(dlqTopic,
                  record.value().getId().toString(),
                  new GSONSerializer<>().serialize("", record.value()),
                  record.value().getId().append(dlqTopic));
            }
          }
        }
      }
    }
  }

  private Properties getProperties(String groupId,
      Map<String, String> overrideProperties) {
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        GSONDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
    properties.putAll(overrideProperties);
    return properties;
  }

  @Override
  public void close() {
    consumer.close();
  }
}
