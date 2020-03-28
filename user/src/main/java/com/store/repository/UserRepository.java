package com.store.repository;

import com.store.domain.User;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserRepository {
  private Set<User> repository = new HashSet<>();

  public void createUser(String email) {
    System.out.println("Creating user for email " + email);
    repository.add(new User(UUID.randomUUID().toString(), email));
  }

  public boolean exists(String email) {
    return repository.contains(new User(null, email));
  }
}
