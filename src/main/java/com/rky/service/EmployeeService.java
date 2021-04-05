package com.rky.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import com.rky.entity.Employee;

/**
 * @author admin
 *
 */
public interface EmployeeService {
	
	@Async("myExecutor")
	public CompletableFuture<Employee> saveEmployee(final Employee employee);
	public List<Employee> getEmployee();
	public Optional<Employee> getEmployeeById(final Integer id);
	public void deleteEmployee(Integer id);
	public Employee updateEmployee(Employee employee);
	public List<Employee> getEmployeeByFillter(Employee employee);
	public String clearAllCache();
}
