package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SendEmailParser implements ConsumerFunction<Email> {

  @Override
  public void parse(ConsumerRecord<String, Email> record) {
    System.out
        .println("----------------------------------------------------------------------");
    System.out.println("received email request");
    System.out.println(
        "topic: " + record.topic() + " | value: " + record.value() + " | offset: " + record
            .offset() + " | partition: " + record.partition());
    System.out.println("----------------------------------------------------------------------");
  }
}
