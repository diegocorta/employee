package pm.employee.api.service.employee;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.employee.EmployeeContractDto;

public interface IEmployeeContractService
		extends ICommonService<EmployeeContractDto, Long> {
	
	public Collection<EntityModel<EmployeeContractDto>> findAllByEmployeeId(Long employeeId);
	
	public EntityModel<EmployeeContractDto> findLastByEmployeeId(Long employeeId);
	
	public Collection<EntityModel<EmployeeContractDto>> findAllByContractId(Long contractId);
	
	public Collection<EntityModel<EmployeeContractDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds);
	
	public Collection<EntityModel<EmployeeContractDto>> findAllByContractIdIn(Collection<Long> contractIds);

}