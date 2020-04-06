package com.store.consumer;

import com.store.kafka.KafkaReceiver;
import com.store.parser.GenerateReportParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GenerateReport {
  public static final List<String> TOPICS = Collections.singletonList("store.create-report-for-user");

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    try (var kafkaReceiver = new KafkaReceiver<>(
        GenerateReport.class.getSimpleName(),
        TOPICS,
        Map.of(),
        new GenerateReportParser())) {
      kafkaReceiver.run();
    }
  }
}
