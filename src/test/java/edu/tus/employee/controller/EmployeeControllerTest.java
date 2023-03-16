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


@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
	
	@Autowired
	EmployeeController employeeController;
	
	@MockBean
	EmployeeService employeeService;
	

	@Test
	public void addEmployeeTestSuccess() throws EmployeeValidationException
	{
		//Mock the createEmployee method in the Service Layer
		//call addEmployee method in the controller
		//test the httpstatus code return is "CREATED"
		//test the details for the employee object in the message body is correct

		Employee employee = buildEmployee();
		when(employeeService.createEmployee(employee)).thenReturn(employee);
		ResponseEntity<Employee> response = employeeController.addEmployee(employee);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(employee, response.getBody());
		
	}
	
	@Test
	public void addEmployeeTestFail() throws EmployeeValidationException
	{
		//Mock the createEmployee method to throw an EmployeeValidationException with EMPTY_FIELDS method
		//call the addEmployee method in the controller
                //test that the response is "BAD_REQUEST"
                //tests that the correct error mesage is received in the request body 

		Employee employee = buildEmployee();
		when(employeeService.createEmployee(employee)).thenThrow(new EmployeeValidationException(ErrorMessages.EMPTY_FIELDS.getMsg()));
		ResponseEntity<ErrorMessage> response = employeeController.addEmployee(employee);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(), response.getBody().getErrorMessage());
	}
	
	
	Employee buildEmployee() {
		Employee employee = new Employee();
		employee.setAge(20);
		employee.setFirstName("Joe");
		employee.setLastName("Bloggs");
		employee.setEmailAddress("Joe@gmail.com");
		return employee;
	}

}
