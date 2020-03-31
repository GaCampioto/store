package com.store.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.store.model.Message;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class GSONDeserializer<T> implements Deserializer<Message> {

  public static final String TYPE_CONFIG = "com.store.type_config";

  private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageAdapter()).create();

  @Override
  public Message deserialize(String s, byte[] bytes) {
    return gson.fromJson(new String(bytes), Message.class);
  }
}
