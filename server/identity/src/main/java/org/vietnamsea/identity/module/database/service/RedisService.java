package org.vietnamsea.identity.module.database.service;

import java.time.Duration;

public interface RedisService {

  void set(String key, Object value, Duration ttl);

  Object get(String key);

  void delete(String key);

  String getString(String key);

  void setString(String key, String value, Duration ttl);

}
