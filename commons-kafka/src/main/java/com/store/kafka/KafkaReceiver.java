package com.store.kafka;

import com.store.gson.GSONDeserializer;
import java.io.Closeable;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaReceiver<T> implements Closeable {

  public static final int TIMEOUT_IN_MILLIS = 3000;

  private final KafkaConsumer<String, T> consumer;

  public KafkaReceiver(Class<T> type, String groupId, Collection<String> topics,
      Map<String, String> overrideProperties) {
    this.consumer = new KafkaConsumer<>(getProperties(type, groupId, overrideProperties));
    this.consumer.subscribe(topics);
  }

  public KafkaReceiver(Class<T> type, String groupId, Pattern pattern,
      Map<String, String> overrideProperties) {
    this.consumer = new KafkaConsumer<>(getProperties(type, groupId, overrideProperties));
    this.consumer.subscribe(pattern);
  }

  public void run(Consumer<ConsumerRecord<String, T>> parse) {
    while (true) {
      ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(TIMEOUT_IN_MILLIS));
      if (!records.isEmpty()) {
        records.forEach(parse::accept);
      }
    }
  }

  private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        GSONDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
    properties.setProperty(GSONDeserializer.TYPE_CONFIG, type.getName());
    properties.putAll(overrideProperties);
    return properties;
  }

  @Override
  public void close() {
    consumer.close();
  }
}
