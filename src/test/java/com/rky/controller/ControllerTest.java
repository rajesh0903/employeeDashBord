package com.rky.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rky.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private EmployeeService empservice;
	@InjectMocks
	private Controller controller;
	
	String emp="";

	@Before
	void setUp() throws Exception {
		
		 emp="{\r\n"
				+ "    \"id\": 20,\r\n"
				+ "    \"firstName\": \"Sing\",\r\n"
				+ "    \"lastName\": \"yadav\",\r\n"
				+ "    \"address\": [\r\n"
				+ "        {\r\n"
				+ "            \"pincode\": 56000,\r\n"
				+ "            \"city\": \"Delhi\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testAddEmployee() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/newEmployee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(emp)
				.accept(MediaType.APPLICATION_JSON))
		
		.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdateEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateEmployee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(emp)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetAllEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/emplist").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetEmployeeById() throws Exception {
		
		String s="{\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"firstName\": \"Sing\",\r\n"
				+ "        \"lastName\": \"yadav\",\r\n"
				+ "        \"address\": [\r\n"
				+ "            {\r\n"
				+ "                \"pincode\": 56000,\r\n"
				+ "                \"city\": \"Delhi\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/emplist/id/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(s));
	}

	@Test
	void testGetEmployeeByFilter() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/filter").accept(MediaType.APPLICATION_JSON))		
		.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testDeleteEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/delete/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
