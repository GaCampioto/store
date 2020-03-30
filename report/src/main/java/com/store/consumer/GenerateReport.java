package com.store.consumer;

import com.store.domain.User;
import com.store.kafka.KafkaReceiver;
import com.store.parser.GenerateReportParser;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenerateReport {
  public static final List<String> TOPICS = Collections.singletonList("store.create-report-for-user");

  public static void main(String[] args) {
    GenerateReportParser generateReportParser = new GenerateReportParser();
    try (var kafkaReceiver = new KafkaReceiver<>(
        User.class,
        GenerateReport.class.getSimpleName(),
        TOPICS,
        Map.of(),
        generateReportParser)) {
      kafkaReceiver.run();
    }
  }
}
