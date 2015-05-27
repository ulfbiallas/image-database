package de.ulfbiallas.imagedatabase.tools;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
@EnableCaching
public class Cache {

    @Bean
    CacheManager cacheManager() {

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
            new ConcurrentMapCache("imagerecord"),
            new ConcurrentMapCache("tag")
        ));

        return cacheManager;
    }

}
