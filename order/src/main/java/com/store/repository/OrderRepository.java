package com.store.repository;

import com.store.LocalRepository;
import com.store.domain.Order;
import java.sql.SQLException;

public class OrderRepository {

  private final LocalRepository localRepository;

  public OrderRepository() throws SQLException {
    String name = "orders_database";
    String createTable = "create table Orders (" +
        "id varchar(200) primary key," +
        "price number," +
        "free_shipping boolean)";
    localRepository = new LocalRepository(name);
    localRepository.createIfNotExists(createTable);
  }

  public void createOrder(Order order) throws SQLException {
    String insert = "insert into Orders (id, price, free_shipping) " +
        "values (?,?, ?)";
    localRepository
        .insert(insert, String.valueOf(order.getOrderNumber()), order.getPrice().toString(), "true");
  }

  public boolean exists(Order order) throws SQLException {
    return localRepository.query("select id from Orders " +
        "where id = ? limit 1", String.valueOf(order.getOrderNumber())).next();
  }
}
