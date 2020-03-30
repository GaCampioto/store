package com.store.parser;

import com.store.ConsumerFunction;
import com.store.domain.User;
import com.store.io.FileManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class GenerateReportParser implements ConsumerFunction<User> {


  public static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

  @Override
  public void parse(ConsumerRecord<String, User> record)
      throws IOException {
    System.out
        .println("----------------------------------------------------------------------");
    User user = record.value();
    System.out.println("creating report for user " + user.getId());
    File target = new File(user.getRelativePath());
    FileManager.copyTo(SOURCE, target);
    FileManager.append(target, "created for " + user.getId());
    System.out.println("report created for user " + user.getId() + " in " + target.getAbsolutePath());
    System.out
        .println("----------------------------------------------------------------------");
  }
}
