package com.store.domain;

import java.math.BigDecimal;

public class Order {

  public Order(String userEmail, int orderNumber, BigDecimal price) {
    this.userEmail = userEmail;
    this.orderNumber = orderNumber;
    this.price = price;
  }

  private String userEmail;
  private int orderNumber;
  private BigDecimal price;

  public String getUserEmail() {
    return userEmail;
  }

  public int getOrderNumber() {
    return orderNumber;
  }
}
