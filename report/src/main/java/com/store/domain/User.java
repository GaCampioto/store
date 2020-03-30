package com.store.domain;

public class User {
  private String id;

  public String getId() {
    return id;
  }

  public String  getRelativePath() {
    return "target/" + id + "-report.txt";
  }
}
