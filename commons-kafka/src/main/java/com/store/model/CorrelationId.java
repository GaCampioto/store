package com.store.model;

import java.util.UUID;
import lombok.ToString;

@ToString
public class CorrelationId {
  private final String correlationId;

  public CorrelationId() {
    this.correlationId = UUID.randomUUID().toString();
  }
}
