package edu.tus.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.tus.employee.errors.ErrorMessage;
import edu.tus.employee.exception.EmployeeException;
import edu.tus.employee.model.Employee;
import edu.tus.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	@PostMapping // (value="/employees")
	public ResponseEntity addEmployee(@RequestBody Employee emp) {
		try {
			Employee savedEmployee = empService.createEmployee(emp);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
		} catch (EmployeeException e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}

}



