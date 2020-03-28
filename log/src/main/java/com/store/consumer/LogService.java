package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.LogParser;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

  public static final Pattern ALL_STORE = Pattern.compile("store.*");
  public static final Map<String, String> CUSTOM_PROPERTIES = Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
      StringDeserializer.class.getName());

  public static void main(String[] args) {
    LogParser logParser = new LogParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        String.class,
        LogService.class.getSimpleName(),
        ALL_STORE,
        CUSTOM_PROPERTIES,
        logParser)) {
      kafkaReceiver.run();
    }
  }
}
