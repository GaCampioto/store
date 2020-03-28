package com.store;

import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {
  void parse(ConsumerRecord<String, T> record) throws ExecutionException, InterruptedException;
}
