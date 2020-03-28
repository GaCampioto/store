package com.store.domain;

import java.math.BigDecimal;

public class Order {

  public Order(int orderNumber, BigDecimal price) {
    this.orderNumber = orderNumber;
    this.price = price;
  }

  private int orderNumber;
  private BigDecimal price;

  public int getOrderNumber() {
    return orderNumber;
  }

  public boolean isFreeShipping() {
    return price.compareTo(BigDecimal.valueOf(100.0)) >= 0;
  }
}
