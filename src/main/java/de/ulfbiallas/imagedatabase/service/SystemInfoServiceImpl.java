package de.ulfbiallas.imagedatabase.service;

import javax.inject.Inject;

import net.sf.ehcache.Cache;
import net.sf.ehcache.statistics.StatisticsGateway;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

@Component
public class SystemInfoServiceImpl implements SystemInfoService {

    private final CacheManager cacheManager;

    @Inject
    public SystemInfoServiceImpl(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public String getSystemInfo() {
        String info = "<h1>Image Database</h1>";

        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        info += "<b>OS</b>: " + osName + " (" + osArch + ")<br>";

        String javaVendor = System.getProperty("java.vendor");
        String javaVersion = System.getProperty("java.version");
        info += "<b>Java</b>: " + javaVendor + " (" + javaVersion + ")<br>";

        EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
        for (String name : ehCacheCacheManager.getCacheNames()) {
            info += "<br>";
            info += "<b>Cache</b>: " + name + "<br>";
            Cache cache = ehCacheCacheManager.getCacheManager().getCache(name);
            StatisticsGateway stats = cache.getStatistics();
            info += "localHeapSizeInBytes: " + stats.getLocalHeapSizeInBytes()
                    + "<br>";
            info += "cacheHitCount: " + stats.cacheHitCount() + "<br>";
            info += "cacheMissCount: " + stats.cacheMissCount() + "<br>";
        }

        return info;
    }

}
