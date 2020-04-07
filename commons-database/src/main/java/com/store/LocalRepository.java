package com.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalRepository {
  private final Connection connection;

  public LocalRepository(String name) throws SQLException {
    String url = "jdbc:sqlite:target/" + name + ".db";
    connection = DriverManager.getConnection(url);
  }

  public void createIfNotExists(String sql) {
    try {
      connection.createStatement().execute(sql);
    } catch(SQLException ex) {
      ex.printStackTrace();
    }
  }

  public boolean insert(String statement, String... params) throws SQLException {
    return prepare(statement, params).execute();
  }

  public ResultSet query(String query, String... params) throws SQLException {
    return prepare(query, params).executeQuery();
  }

  private PreparedStatement prepare(String statement, String[] params) throws SQLException {
    var preparedStatement = connection.prepareStatement(statement);
    for (int i = 0; i < params.length; i++) {
      preparedStatement.setString(i + 1, params[i]);
    }
    return preparedStatement;
  }
}
