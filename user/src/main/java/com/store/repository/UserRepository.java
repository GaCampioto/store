package com.store.repository;

import com.store.LocalRepository;
import com.store.domain.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {
  private final LocalRepository localRepository;

  public UserRepository() throws SQLException {
    String name = "users_database";
    String createTable = "create table Users (" +
          "id varchar(200) primary key," +
          "email varchar(200))";
    localRepository = new LocalRepository(name);
    localRepository.createIfNotExists(createTable);
  }

  public void createUser(String email) throws SQLException {
    System.out.println("Creating user for email " + email);
    String insert = "insert into Users (id, email) " +
        "values (?,?)";
    String userId = UUID.randomUUID().toString();
    localRepository.insert(insert, userId, email);
    System.out.println("user id " + userId + " and email " + email + " created");
  }

  public boolean exists(String email) throws SQLException {
    String exists = "select id from Users " +
        "where email = ? limit 1";
    return localRepository.query(exists, email).next();
  }

  public List<User> getAll() throws SQLException {
    var results = localRepository.query("select id from Users");
    List<User> users = new ArrayList<>();
    while(results.next()) {
      users.add(new User(results.getString(1), null));
    }
    return users;
  }
}
