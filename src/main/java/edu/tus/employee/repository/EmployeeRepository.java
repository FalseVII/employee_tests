package edu.tus.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.tus.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByEmailAddress(String emailId);

}