package com.store.repository;

import com.store.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserRepository {
  private Set<User> repository = new HashSet<>();
  private final Connection connection;

  public UserRepository() throws SQLException {
    String url = "jdbc:sqlite:target/users_database.db";
    connection = DriverManager.getConnection(url);
    try {
      connection.createStatement().execute("create table Users (" +
          "id varchar(200) primary key," +
          "email varchar(200))");
    } catch(SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void createUser(String email) throws SQLException {
    System.out.println("Creating user for email " + email);
    var insert = connection.prepareStatement("insert into Users (id, email) " +
        "values (?,?)");
    String userId = UUID.randomUUID().toString();
    insert.setString(1, userId);
    insert.setString(2, email);
    insert.execute();
    System.out.println("user id " + userId + " and email " + email + " created");
  }

  public boolean exists(String email) throws SQLException {
    var exists = connection.prepareStatement("select id from Users " +
        "where email = ? limit 1");
    exists.setString(1, email);
    var results = exists.executeQuery();
    return results.next();
  }

  public List<User> getAll() throws SQLException {
    var results = connection.prepareStatement("select id from Users").executeQuery();
    List<User> users = new ArrayList<>();
    while(results.next()) {
      users.add(new User(results.getString(1), null));
    }
    return users;
  }
}
