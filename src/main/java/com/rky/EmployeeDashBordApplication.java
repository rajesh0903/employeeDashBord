package com.rky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeeDashBordApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDashBordApplication.class, args);
	}
	
	
	/*
	 * @Autowired() private CacheService cachService;
	 * 
	 * @Autowired private EmployeeService empService;
	 * 
	 * @PostConstruct public void loadData() { empService.getEmployee(); }
	 */
	

}
