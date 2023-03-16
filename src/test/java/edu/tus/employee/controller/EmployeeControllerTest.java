package edu.tus.employee.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import edu.tus.employee.service.EmployeeService;
import edu.tus.employee.errors.ErrorMessage;
import edu.tus.employee.errors.ErrorMessages;
import edu.tus.employee.exception.EmployeeValidationException;
import edu.tus.employee.model.Employee;
import static org.mockito.Mockito.when;


// Annotate the class to indicate this is a Spring Boot test
// and enable the auto-configuration of the MockMvc
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

	// Inject the EmployeeController instance into the test
	@Autowired
	EmployeeController employeeController;

	// Create a mock instance of the EmployeeService for testing
	@MockBean
	EmployeeService employeeService;

	// Test case for successful employee creation
	@Test
	public void addEmployeeTestSuccess() throws EmployeeValidationException
	{
		// Create an Employee object with valid data
		Employee employee = buildEmployee();

		// Mock the createEmployee method in the EmployeeService to return the employee object
		when(employeeService.createEmployee(employee)).thenReturn(employee);

		// Call the addEmployee method in the EmployeeController and store the response
		ResponseEntity<Employee> response = employeeController.addEmployee(employee);

		// Assert that the response status code is CREATED (201)
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Assert that the response body contains the correct employee object
		assertEquals(employee, response.getBody());
	}

	// Test case for failed employee creation
	@Test
	public void addEmployeeTestFail() throws EmployeeValidationException
	{
		// Create an Employee object with valid data
		Employee employee = buildEmployee();

		// Mock the createEmployee method in the EmployeeService to throw an EmployeeValidationException
		when(employeeService.createEmployee(employee)).thenThrow(new EmployeeValidationException(ErrorMessages.EMPTY_FIELDS.getMsg()));

		// Call the addEmployee method in the EmployeeController and store the response
		ResponseEntity<ErrorMessage> response = employeeController.addEmployee(employee);

		// Assert that the response status code is BAD_REQUEST (400)
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Assert that the response body contains the correct error message
		assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(), response.getBody().getErrorMessage());
	}

	// Helper method to create an Employee object with sample data
	Employee buildEmployee() {
		Employee employee = new Employee();
		employee.setAge(20);
		employee.setFirstName("Joe");
		employee.setLastName("Bloggs");
		employee.setEmailAddress("Joe@gmail.com");
		return employee;
	}

}