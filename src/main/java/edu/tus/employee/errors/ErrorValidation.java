package edu.tus.employee.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.tus.employee.model.Employee;
import edu.tus.employee.repository.EmployeeRepository;

@Component
public class ErrorValidation {
	
	@Autowired
    EmployeeRepository empRepository;
	
	public boolean emptyFields(Employee employee) {
		return ((employee.getFirstName().length()==0||employee.getLastName().length()==0
				||employee.getEmailAddress().length()==0));
	}
	
	public boolean invalidAge(Employee employee) {
		return ((employee.getAge()<18||employee.getAge()>65)); 
	}

}
