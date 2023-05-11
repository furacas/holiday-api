package com.furacas.api.holiday.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtils {

  private static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @SneakyThrows
  public static <T> T fromJson(String str, Class<T> clazz) {
    return mapper.readValue(str, clazz);
  }

  @SneakyThrows
  public static String toJson(Object obj) {
    return mapper.writeValueAsString(obj);
  }
}
