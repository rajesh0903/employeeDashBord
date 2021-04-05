package com.rky.config;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
//import net.sf.ehcache.loader.CacheLoader;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer 
{
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(CacheConfig.class);	
	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		// cache for Employee
		net.sf.ehcache.config.CacheConfiguration cacheConfig = new net.sf.ehcache.config.CacheConfiguration();
		cacheConfig.setName("EmployeeCache");
		cacheConfig.setMemoryStoreEvictionPolicy("LRU");
		cacheConfig.setMaxEntriesLocalHeap(1000);
		cacheConfig.setTimeToLiveSeconds(300);

		// cache for ID
		net.sf.ehcache.config.CacheConfiguration cacheConfig1 = new net.sf.ehcache.config.CacheConfiguration();
		cacheConfig1.setName("EmployeeCacheById");
		cacheConfig1.setMemoryStoreEvictionPolicy("LRU");
		cacheConfig1.setMaxEntriesLocalHeap(10);
		cacheConfig1.setTimeToLiveSeconds(300);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(cacheConfig);
		config.addCache(cacheConfig1);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}
	
	@Bean
	public void caCheCheck() throws ExecutionException {
		Ehcache cache = (Ehcache) cacheManager().getCache("EmployeeCache").getNativeCache();		
		Status status = cache.getStatus();		
		if ((!Status.STATUS_ALIVE.equals(status))) {			
			
		}		
	}
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

	@Override
	public CacheResolver cacheResolver() {
		
		return null;
	}

	@Override
	public KeyGenerator keyGenerator() {
		
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		
		return null;
	}	
}
