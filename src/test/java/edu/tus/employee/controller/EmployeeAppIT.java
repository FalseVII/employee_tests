// Declare package for this class
package edu.tus.employee.controller;

// Import necessary classes and packages
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

// Annotate the class to indicate this is a Spring Boot test
// and use a random port for testing the web environment
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeAppIT {

	// Inject the value of the local server port
	@Value(value = "${local.server.port}")
	private int port;

	// Declare the TestRestTemplate and HttpHeaders objects
	TestRestTemplate restTemplate;
	HttpHeaders headers;

	// Before each test, create a new TestRestTemplate
	// and set the headers' content type to JSON
	@BeforeEach
	public void setup() {
		restTemplate = new TestRestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	// Test case for adding a new employee successfully
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeSuccessIntTest() {
		// Create a new Employee object with valid data
		Employee employee = buildEmployee();

		// Create an HttpEntity with the employee and headers
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

		// Send a POST request to the API endpoint and store the response
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);

		// Assert that the response status code is CREATED (201)
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	// Test case for adding an employee with an empty firstName field
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeEmptyFieldIntTest() {
		// Create a new Employee object with an empty firstName
		Employee employee = buildEmployee();
		employee.setFirstName("");

		// Create an HttpEntity with the employee and headers
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

		// Send a POST request to the API endpoint and store the response
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);

		// Assert that the response status code is BAD_REQUEST (400)
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Test case for adding an employee with an already existing email address
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeEmailAlreadyExistsIntTest() {
		// Create a new Employee object with valid data
		Employee employee = buildEmployee();

		// Create an HttpEntity with the employee and headers
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

		// Send a POST request to the API endpoint and store the response
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);

		// Assert that the response status code is CREATED (201)
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		// Create another Employee object with the same email address
		employee = buildEmployee();

		// Create a new HttpEntity with the duplicate employee and headers
		entity = new HttpEntity<>(employee, headers);

		// Send a POST request to the API endpoint and store the response
		response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);

		// Assert that the response status code is BAD_REQUEST (400)
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Test case for adding an employee with an invalid age
	@Test
	@Sql({"/testdata.sql"})
	public void addEmployeeAgeInvalidIntTest() {
		// Create a new Employee object with an invalid age
		Employee employee = buildEmployee();
		employee.setAge(17);

		// Create an HttpEntity with the employee and headers
		HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

		// Send a POST request to the API endpoint and store the response
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/employees", entity, String.class);

		// Assert that the response status code is BAD_REQUEST (400)
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	// Helper method to create an Employee object with sample data
	Employee buildEmployee() {
		Employee employee = new Employee();
		employee.setAge(20);
		employee.setFirstName("Joe");
		employee.setLastName("Bloggs");
		employee.setEmailAddress("joe@gmail.com");
		return employee;
	}
}