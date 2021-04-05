package com.rky.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rky.dto.EmployeeDTO;
import com.rky.repository.EmployeeRepository;
import com.rky.service.EmployeeService;

@RunWith(SpringRunner.class)
@DataMongoTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepository remprepo;	
	
	@InjectMocks
	private EmployeeService empService;

	@Before
	public void setUp() throws Exception {
		System.out.println("service class testing");
	}

	@Test
	public void testSaveEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEmployee() {
		
		List<EmployeeDTO> empList=empService.getEmployee();
		assertEquals(10, empList.size());
	}

	@Test
	public void testGetEmployeeById() {
		EmployeeDTO emp=empService.getEmployeeById(1);
		assertEquals(emp.getFirstName(),"Sing");
	}

	@Test
	public void testDeleteEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEmployeeByFillter() {
		fail("Not yet implemented");
	}

}
