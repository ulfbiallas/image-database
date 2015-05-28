package de.ulfbiallas.imagedatabase.tools;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
@EnableCaching
public class Cache {

    private final String LEAST_FREQUENTLY_USED = "LFU";



    @Bean
    CacheManager cacheManager() {
        net.sf.ehcache.config.Configuration ehCacheConfiguration = createEhCacheConfiguration();
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.newInstance(ehCacheConfiguration);

        cacheManager.addCache("imagerecord");
        cacheManager.addCache("tag");

        return new EhCacheCacheManager(cacheManager);
    }



    private net.sf.ehcache.config.Configuration createEhCacheConfiguration() {
        net.sf.ehcache.config.Configuration ehCacheConfiguration = new net.sf.ehcache.config.Configuration();
        ehCacheConfiguration.addDefaultCache(createEhCacheCacheConfiguration());
        return ehCacheConfiguration;
    }



    private net.sf.ehcache.config.CacheConfiguration createEhCacheCacheConfiguration() {
        net.sf.ehcache.config.CacheConfiguration ehCacheCacheConfiguration = new net.sf.ehcache.config.CacheConfiguration();
        ehCacheCacheConfiguration.setName("ImageDatabaseCache");
        ehCacheCacheConfiguration.setMemoryStoreEvictionPolicy(LEAST_FREQUENTLY_USED);
        ehCacheCacheConfiguration.setMaxEntriesLocalHeap(1000);
        return ehCacheCacheConfiguration;
    }

}