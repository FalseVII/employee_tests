package edu.tus.employee.controller;

import edu.tus.employee.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeAppIT {
	
	@Value(value="${local.server.port}")
	private int port;
	
	TestRestTemplate restTemplate;
	HttpHeaders headers;
	
	@BeforeEach
	public void setup() {
		restTemplate =new TestRestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeSuccessIntTest()
	{
		
		//Use TestRestTemplate to call a post with valid data
		// Test that the response code is "CREATED"
		// Note the testdata.sql deletes an employee with email "joe@gmail.com"
		Employee employee = buildEmployee();
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeEmptyFieldIntTest()
	{
		//Use TestRestTemplate to post data where firstName is empty
		//Test that status code BAD_REQUEST is returned

		Employee employee = buildEmployee();
		employee.setFirstName("");
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeEmailAlreadyExistsIntTest()
	{
		//Use TestRestTemplate to add an employee with existing email addesss
		//Test that status code BAD_REQUEST is returned

		Employee employee = buildEmployee();
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		employee = buildEmployee();
		entity = new HttpEntity<>(employee, headers);
		response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeAgeInvalidIntTest()
	{
		//Use TestRestTemplate to add an employee with age not in correct range
		//Test that status code BAD_REQUEST is returned

		Employee employee = buildEmployee();
		employee.setAge(17);
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}
	
	Employee buildEmployee() {
		Employee employee = new Employee();
		employee.setAge(20);
		employee.setFirstName("Joe");
		employee.setLastName("Bloggs");
		employee.setEmailAddress("joe@gmail.com");
		return employee;
	}

}
