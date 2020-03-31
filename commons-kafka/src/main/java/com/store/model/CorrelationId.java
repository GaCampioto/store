package com.store.model;

import java.util.UUID;
import lombok.ToString;

@ToString
public class CorrelationId {
  private final String id;

  public CorrelationId(String title) {
    this.id = title + "(" + UUID.randomUUID().toString() + ")";
  }

  public CorrelationId append(String title) {
    return new CorrelationId(id + "-" + title);
  }
}
