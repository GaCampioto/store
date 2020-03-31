package com.store.parser;

import com.store.ConsumerFunction;
import com.store.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogParser implements ConsumerFunction<String> {

  @Override
  public void parse(ConsumerRecord<String, Message<String>> record) {
    System.out.println(
        "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
            .offset() + " | partition: " + record.partition());
  }
}
