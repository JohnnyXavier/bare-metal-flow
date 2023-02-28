package com.bmc.flow.modules.service.base;

import io.quarkus.cache.CacheKeyGenerator;
import lombok.extern.jbosslog.JBossLog;

import java.lang.reflect.Method;

@JBossLog
public class CountAllCKGen implements CacheKeyGenerator {

  @Override
  public Object generate(final Method method, final Object... methodParams) {
    String dtoClassName = methodParams[0].toString();
    String cacheKey = dtoClassName.substring(dtoClassName.lastIndexOf(".")+1);
    log.debugf("generated cache key: [%s]", cacheKey);
    return cacheKey;
  }
}
