package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.User;
import com.store.io.FileManager;
import com.store.model.Message;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class GenerateReportParser implements ConsumerFunction<User> {


  public static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

  @Override
  public void parse(ConsumerRecord<String, Message<User>> record)
      throws IOException {
    Message<User> message = record.value();
    User user = message.getPayload();
    System.out.println("creating report for user " + user.getId());
    File target = new File(user.getRelativePath());
    FileManager.copyTo(SOURCE, target);
    FileManager.append(target, "created for " + user.getId());
    System.out.println("report created for user " + user.getId() + " in " + target.getAbsolutePath());
  }
}
