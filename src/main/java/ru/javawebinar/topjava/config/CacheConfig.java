package ru.javawebinar.topjava.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.cache.CacheManager;
import java.io.IOException;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheCacheManager ehCacheManager(CacheManager cm)
    {
        return new JCacheCacheManager(cm);
    }

    @Bean
    public JCacheManagerFactoryBean ehCache() throws IOException {
        JCacheManagerFactoryBean managerFactoryBean = new JCacheManagerFactoryBean();
        managerFactoryBean.setCacheManagerUri(new ClassPathResource("cache/ehcache.xml").getURI());
        return managerFactoryBean;
    }

//    @Bean
//    public EhCacheCacheManager cacheManager(CacheManager cm)
//    {
//        return new EhCacheCacheManager(cm);
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCache() {
//        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
//        return ehCacheManagerFactoryBean;
//    }
}
