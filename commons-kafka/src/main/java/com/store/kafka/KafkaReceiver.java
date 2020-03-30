package com.store.kafka;

import com.store.ConsumerFunction;
import com.store.gson.GSONDeserializer;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaReceiver<T> implements Closeable {

  public static final int TIMEOUT_IN_MILLIS = 3000;

  private final KafkaConsumer<String, T> consumer;
  private final ConsumerFunction<T> consumerFunction;

  public KafkaReceiver(Class<T> type, String groupId, Collection<String> topics,
      Map<String, String> overrideProperties, ConsumerFunction<T> consumerFunction) {
    this(type, groupId,overrideProperties, consumerFunction);
    this.consumer.subscribe(topics);
  }

  public KafkaReceiver(Class<T> type, String groupId, Pattern pattern,
      Map<String, String> overrideProperties, ConsumerFunction<T> consumerFunction) {
    this(type, groupId,overrideProperties, consumerFunction);
    this.consumer.subscribe(pattern);
  }

  private KafkaReceiver(Class<T> type, String groupId, Map<String, String> overrideProperties,
      ConsumerFunction<T> consumerFunction) {
    this.consumer = new KafkaConsumer<>(getProperties(type, groupId, overrideProperties));
    this.consumerFunction = consumerFunction;
  }

  public void run() {
    while (true) {
      ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(TIMEOUT_IN_MILLIS));
      if (!records.isEmpty()) {
        records.forEach(record -> {
          try {
            consumerFunction.parse(record);
          } catch (ExecutionException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
      }
    }
  }

  private Properties getProperties(Class<T> type, String groupId,
      Map<String, String> overrideProperties) {
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
