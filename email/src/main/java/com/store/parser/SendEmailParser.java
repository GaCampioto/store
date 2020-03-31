package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.Email;
import com.store.model.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SendEmailParser implements ConsumerFunction<Email> {

  @Override
  public void parse(ConsumerRecord<String, Message<Email>> record) {
    System.out.println("email: " + record.value().getPayload());
  }
}
