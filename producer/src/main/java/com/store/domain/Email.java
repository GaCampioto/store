package com.store.domain;

public class Email {

  private String userEmail;
  private String subject;
  private String content;

  public Email(String userEmail, String subject, String content) {
    this.userEmail = userEmail;
    this.subject = subject;
    this.content = content;
  }
}
