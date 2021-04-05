package com.rky.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rky.entity.Employee;
import com.rky.exception.CustomException;
import com.rky.repository.EmployeeRepository;
import com.rky.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository empRepo;

	@Caching(put = { @CachePut(value = "EmployeeCacheById", key = "#employee.get(0).id"),
			@CachePut(value = "EmployeeCache") })
	@Override
	public CompletableFuture<Employee> saveEmployee(final Employee employee) {
		logger.info("Current executing Thread::{}",Thread.currentThread().getName());
		return CompletableFuture.completedFuture(empRepo.save(employee));
	}

	@Override
	@Cacheable(value = "EmployeeCache")
	public List<Employee> getEmployee() {
		logger.info("Data fetching from Database::");
		// PageRequest page=PageRequest.of(pagenumber, 5)
		return empRepo.findAll();
	}
	
	@Cacheable(value = "EmployeeCacheById", key = "#id")
	@Override
	public Optional<Employee> getEmployeeById(final Integer id) {
		logger.info("Data fetching from Database for: {}",id);
		return empRepo.findById(id);
	}

	@CacheEvict(value = "EmployeeCacheById", key = "#id")
	@Override
	public void deleteEmployee(Integer id) {
		if (empRepo.existsById(id)) {
			empRepo.deleteById(id);
		} else {
			throw new CustomException("Record not found in database");
		}
	}

	@Caching(put = { @CachePut(value = "EmployeeCacheById", key = "#employee.id"), @CachePut(value = "EmployeeCache") })
	@Override
	public Employee updateEmployee(Employee employee) {
		if (empRepo.existsById(employee.getId())) {
			return empRepo.save(employee);
		}
		throw new CustomException("Record not foud in Database to update");
	}

	@CachePut(value = "EmployeeCacheById", key = "#employee.get().id")
	@Override
	public List<Employee> getEmployeeByFillter(Employee employee) {
		Example<Employee> emp = Example.of(employee);
		return empRepo.findAll(emp);
	}

	@CacheEvict(value = { "EmployeeCacheById", "EmployeeCache" }, allEntries = true)
	@Override
	public String clearAllCache() {
		return "All Cache cleared by you..";
	}
}
