package com.store.domain;

import java.math.BigDecimal;

public class Order {
  private int orderNumber;
  private String userEmail;
  private BigDecimal price;

  public String getUserEmail() {
    return userEmail;
  }
}
