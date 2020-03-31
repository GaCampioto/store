package com.store;

import com.store.model.Message;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {
  void parse(ConsumerRecord<String, Message<T>> record)
      throws ExecutionException, InterruptedException, IOException, SQLException;
}
