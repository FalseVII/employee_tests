package edu.tus.employee.errors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.tus.employee.model.Employee;

class ErrorValidationTest {
	ErrorValidation errorValidation;
	Employee employee;

	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		employee = buildEmployee();
	
	}

	@Test
	void testAllFieldsValid() {
		assertFalse(errorValidation.emptyFields(employee));
	}

	@Test
	void testEmptyFieldFirstName() {
		employee.setFirstName("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	@Test
	void testValidAge() {
		employee.setAge(20);
		assertFalse(errorValidation.invalidAge(employee));
	}

	@Test
	void testEmptyFieldLastName() {
		employee.setLastName("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	@Test
	void testEmptyFieldEmail() {
		employee.setEmailAddress("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	@Test
	void testInvalidAgeLow() {

		employee.setAge(17);
		assertTrue(errorValidation.invalidAge(employee));
		
	}

	@Test
	void testInvalidAgeHigh() {

		employee.setAge(66);
		assertTrue(errorValidation.invalidAge(employee));
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
