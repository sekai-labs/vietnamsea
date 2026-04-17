package org.vietnamsea.identity.module.database.service.impl;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.module.database.service.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void set(String key, Object value, Duration ttl) {
    redisTemplate.opsForValue().set(key, value, ttl);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public String getString(String key) {
    Object val = get(key);
    return val != null ? val.toString() : null;
  }

  @Override
  public void setString(String key, String value, Duration ttl) {
    set(key, value, ttl);
  }
}
