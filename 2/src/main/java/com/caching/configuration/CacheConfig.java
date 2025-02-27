package com.caching.configuration;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableScheduling // Enable scheduled tasks
public class CacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("geocoding", "reverse-geocoding");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(50) // Maximum 50 entries in the cache
                .expireAfterAccess(5, TimeUnit.MINUTES) // Expire entries 5 minutes after last access
                .recordStats() // Track stats for eviction and access frequency
                .evictionListener((key, value, cause) -> {
                    if (cause == RemovalCause.SIZE) {
                        logger.info("Evicted entry due to LFU (SIZE eviction): Key = {}, Value = {}", key, value);
                    } else if (cause == RemovalCause.EXPIRED) {
                        logger.info("Evicted entry due to EXPIRATION (expired after 5 minutes): Key = {}, Value = {}", key, value);
                    }
                });
    }
}
