package com.store.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.store.model.Message;
import org.apache.kafka.common.serialization.Serializer;

public class GSONSerializer<T> implements Serializer<T> {

  private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageAdapter()).create();

  @Override
  public byte[] serialize(String s, T object) {
    return gson.toJson(object).getBytes();
  }
}
