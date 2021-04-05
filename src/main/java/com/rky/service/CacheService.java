package com.rky.service;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;

@Component
public class CacheService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(CacheService.class);

	@Autowired
	private CacheManager cacheManager1;
	@Autowired
	private EmployeeService empService;

	@org.springframework.scheduling.annotation.Scheduled(cron = "0 0/2 * * * ?")
	public void refreshAllcachesAtIntervals() {
		Ehcache cache = (Ehcache) cacheManager1.getCache("EmployeeCache").getNativeCache();
		Status status = cache.getStatus();
		if ((Status.STATUS_ALIVE.equals(status))) {
			logger.info("Cache status is :: {}", Status.STATUS_ALIVE);
		}
		logger.info("Cache refresh time:: {}", new Date().getTime());
		cacheManager1.getCache("EmployeeCache").invalidate();
		// Reloading data into cache
		empService.getEmployee().forEach(emp -> cacheManager1.getCache("EmployeeCache").putIfAbsent(emp, emp));

	}

	/*private com.google.common.cache.LoadingCache<Integer, Employee> cachLoader = CacheBuilder.newBuilder()
			.refreshAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<Integer, Employee>() {
				@Override
				public Employee load(Integer key) throws Exception {
					logger.info("GUAVA Cache started::");
					return empRepo.findById(key).get();
				}
			});

	public Employee loadData(Integer id) throws ExecutionException {
		return cachLoader.get(id);
			}*/

	/*
	 * private void errorHandler() { Ehcache cache = (Ehcache)
	 * cacheManager1.getCache("EmployeeCache").getNativeCache(); Status status =
	 * cache.getStatus(); System.out.println("cache status:: "+Status.STATUS_ALIVE);
	 * if ((!Status.STATUS_ALIVE.equals(status))) {
	 * System.out.println("Error in cache"); }
	 * 
	 * }
	 */

}
