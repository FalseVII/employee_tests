package edu.tus.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.tus.employee.errors.ErrorMessages;
import edu.tus.employee.errors.ErrorValidation;
import edu.tus.employee.exception.EmployeeValidationException;
import edu.tus.employee.model.Employee;
import edu.tus.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	Employee employee;

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	ErrorValidation errorValidation;

	// CREATE
	public Employee createEmployee(Employee emp) throws EmployeeValidationException {
		this.employee = emp;
		if (errorValidation.emptyFields(employee)) {
			throw new EmployeeValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
		if (empRepository.findByEmailAddress(employee.getEmailAddress()) != null) {
			throw new EmployeeValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());
		}

		if (errorValidation.invalidAge(employee)) {
			throw new EmployeeValidationException(ErrorMessages.INVALID_AGE.getMsg());
		}
		return empRepository.save(employee);
	}

}

