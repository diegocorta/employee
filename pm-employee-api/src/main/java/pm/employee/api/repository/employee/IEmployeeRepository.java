package pm.employee.api.repository.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.Employee;

public interface IEmployeeRepository 
		extends JpaRepository<Employee, Long> {
	
	public Optional<Employee> findOneBysecurityUserId(Long securityUserId);

}
