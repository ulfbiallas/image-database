package de.ulfbiallas.imagedatabase.tools;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
@EnableCaching
public class Cache {

    @Bean
    CacheManager cacheManager() {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getInstance();

        cacheManager.addCache("imagerecord");
        cacheManager.addCache("tag");

        return new EhCacheCacheManager(cacheManager);
    }

}