package com.rky.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rky.dto.EmployeeDTO;
import com.rky.entity.Employee;
import com.rky.exception.CustomException;
import com.rky.service.EmployeeService;

/**
 * @author admin
 *
 */
@RestController
@RequestMapping("/api/v1")
public class Controller {

	@Autowired
	private EmployeeService empService;
	
	@PostMapping("/newEmployee")
	public ResponseEntity<?> addEmployee(@RequestBody List<EmployeeDTO> employee) {
		employee.forEach(emp->{			
				empService.saveEmployee(emp);});		
		return  ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully::");
	}

	@PutMapping("/updateEmployee")
	public String updateEmployee(@RequestBody EmployeeDTO employee) {
		EmployeeDTO emp = empService.updateEmployee(employee);
		return "Resource updated:: with " + emp;
	}

	@GetMapping("/emplist")
	public List<EmployeeDTO> getAllEmployee() {
		return empService.getEmployee().stream().sorted((e1, e2) -> e1.getId().compareTo(e2.getId()))
				.collect(Collectors.toList());
	}

	@GetMapping("/emplist/id/{id}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable("id") final Optional<Integer> id) {	
		if(id.isPresent())
		{
		return ResponseEntity.ok().body(empService.getEmployeeById(id.get()));
		}
		return  ResponseEntity.ok().body("ID shuld not null::");		
	}

	@GetMapping("/emplist/filter")
	public List<Employee> getEmployeeByFilter(@RequestBody EmployeeDTO employee) {
		return empService.getEmployeeByFillter(employee);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") Optional<Integer> id) {		
		empService.deleteEmployee(id.orElseThrow(()-> new CustomException("ID shuld not null::")));
		return ResponseEntity.ok().body("Employee deleted successfully::");
	}
	
	@GetMapping("/clearCache")
	public String clearAllCache()
	{
		return empService.clearAllCache();		
	}	
	
}
