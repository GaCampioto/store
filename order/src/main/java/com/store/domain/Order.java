package com.store.domain;

import java.math.BigDecimal;

public class Order {

  private String userEmail;
  private int orderNumber;
  private BigDecimal price;

  public Order(String userEmail, int orderNumber, BigDecimal price) {
    this.userEmail = userEmail;
    this.orderNumber = orderNumber;
    this.price = price;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public boolean isFreeShipping() {
    return price.compareTo(BigDecimal.valueOf(100.0)) >= 0;
  }
}
