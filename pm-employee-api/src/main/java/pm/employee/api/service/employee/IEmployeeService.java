package pm.employee.api.service.employee;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonMinifiedService;
import pm.employee.common.dto.employee.EmployeeDto;
import pm.employee.common.dto.employee.min.EmployeeMinDto;

public interface IEmployeeService extends ICommonMinifiedService<EmployeeDto, Long, EmployeeMinDto> {

	public EntityModel<EmployeeDto> findEmployeeBySecurityUser(Long securityUserId);
	
}