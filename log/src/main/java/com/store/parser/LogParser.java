package com.store.parser;

import com.store.ConsumerFunction;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogParser implements ConsumerFunction<String> {

  @Override
  public void parse(ConsumerRecord<String, String> record) {
    System.out
        .println("----------------------------------------------------------------------");
    System.out.println("received log");
    System.out.println(
        "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
            .offset() + " | partition: " + record.partition());
    System.out
        .println("----------------------------------------------------------------------");
  }
}
