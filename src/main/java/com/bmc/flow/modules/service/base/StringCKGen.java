package com.bmc.flow.modules.service.base;

import io.quarkus.cache.CacheKeyGenerator;
import lombok.extern.jbosslog.JBossLog;

import java.lang.reflect.Method;

/**
 * this class is a Cache Key generator that will create a key based on the 2nd method parameter.
 * <p>
 * this allows for dynamic keys generation.
 */
@JBossLog
public class StringCKGen implements CacheKeyGenerator {

    @Override
    public Object generate(final Method method, final Object... methodParams) {
        String cacheKey = methodParams[1].toString().toLowerCase();
        log.debugf(":::generated cache key: [%s]", cacheKey);
        return cacheKey;
    }
}
