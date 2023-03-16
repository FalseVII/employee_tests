package edu.tus.employee.errors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.tus.employee.model.Employee;

// Define a test class for the ErrorValidation functionality
class ErrorValidationTest {
	// Declare an instance of the ErrorValidation class and an Employee object
	ErrorValidation errorValidation;
	Employee employee;

	// Before each test, create a new ErrorValidation instance and an Employee object with sample data
	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		employee = buildEmployee();
	}

	// Test case for validating that all fields are non-empty and valid
	@Test
	void testAllFieldsValid() {
		assertFalse(errorValidation.emptyFields(employee));
	}

	// Test case for validating an empty first name field
	@Test
	void testEmptyFieldFirstName() {
		employee.setFirstName("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	// Test case for validating a valid age value
	@Test
	void testValidAge() {
		employee.setAge(20);
		assertFalse(errorValidation.invalidAge(employee));
	}

	// Test case for validating an empty last name field
	@Test
	void testEmptyFieldLastName() {
		employee.setLastName("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	// Test case for validating an empty email address field
	@Test
	void testEmptyFieldEmail() {
		employee.setEmailAddress("");
		assertTrue(errorValidation.emptyFields(employee));
	}

	// Test case for validating an invalid age value below the valid range
	@Test
	void testInvalidAgeLow() {
		employee.setAge(17);
		assertTrue(errorValidation.invalidAge(employee));
	}

	// Test case for validating an invalid age value above the valid range
	@Test
	void testInvalidAgeHigh() {
		employee.setAge(66);
		assertTrue(errorValidation.invalidAge(employee));
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