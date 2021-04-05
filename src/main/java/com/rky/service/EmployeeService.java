package com.rky.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import com.rky.dto.EmployeeDTO;
import com.rky.entity.Employee;

/**
 * @author admin
 *
 */
public interface EmployeeService {
	
	@Async("myExecutor")
	public CompletableFuture<Employee> saveEmployee(final EmployeeDTO employee);
	public List<EmployeeDTO> getEmployee();
	public EmployeeDTO getEmployeeById(final Integer id);
	public void deleteEmployee(Integer id);
	public EmployeeDTO updateEmployee(EmployeeDTO employee);
	public List<Employee> getEmployeeByFillter(EmployeeDTO employee);
	public String clearAllCache();
}
