package com.rky.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rky.dto.EmployeeDTO;
import com.rky.entity.Employee;
import com.rky.exception.CustomException;
import com.rky.repository.EmployeeRepository;
import com.rky.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository empRepo;
	
	
	  @Autowired() 
	  private DozerBeanMapper mapper;;
	 

	@Caching(put = { @CachePut(value = "EmployeeCacheById", key = "#employee.get(0).id"),
			@CachePut(value = "EmployeeCache") })
	@Override
	public CompletableFuture<Employee> saveEmployee(final EmployeeDTO employee) {
		logger.info("Current executing Thread::{}",Thread.currentThread().getName());		
		return CompletableFuture.completedFuture(empRepo.save(mapper.map(employee, Employee.class)));
	}

	@Override
	@Cacheable(value = "EmployeeCache")
	public List<EmployeeDTO> getEmployee() {
		logger.info("Data fetching from Database::");				
		return empRepo.findAll().parallelStream().map(emp->mapper.map(emp, EmployeeDTO.class)).collect(Collectors.toList());
	}
	
	@Cacheable(value = "EmployeeCacheById", key = "#id")
	@Override
	public EmployeeDTO getEmployeeById(final Integer id) {		
		logger.info("Data fetching from Database for: {}",id);		
		return mapper.map(empRepo.findById(id).orElseThrow(()->new CustomException("Record not found in Database")),EmployeeDTO.class);
	}

	@CacheEvict(value = "EmployeeCacheById", key = "#id")
	@Override
	public void deleteEmployee(Integer id) {
		logger.info("Record delete in Database::");	
		if (empRepo.existsById(id)) {
			empRepo.deleteById(id);
		} else {
			throw new CustomException("Record not found in database");
		}
	}

	@Caching(put = { @CachePut(value = "EmployeeCacheById", key = "#employee.id"), @CachePut(value = "EmployeeCache") })
	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO employee) {
		logger.info("Updating record in Database::");	
		if (empRepo.existsById(employee.getId())) {			
			return mapper.map(empRepo.save(mapper.map(employee,Employee.class)),EmployeeDTO.class);
		}
		throw new CustomException("Record not foud in Database to update");
	}

	@CachePut(value = "EmployeeCacheById", key = "#employee.get().id")
	@Override
	public List<Employee> getEmployeeByFillter(EmployeeDTO employee) {	
		logger.info("Searching Data in Database::");	
		Example<Employee> emp = Example.of(mapper.map(employee, Employee.class));
		return empRepo.findAll(emp);
	}

	@CacheEvict(value = { "EmployeeCacheById", "EmployeeCache" }, allEntries = true)
	@Override
	public String clearAllCache() {
		logger.info("All Cache cleared by user::");	
		return "All Cache cleared by you..";
	}
}
