package pm.employee.api.repository.employee;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.EmployeeContract;

public interface IEmployeeContractRepository 
		extends JpaRepository<EmployeeContract, Long> {
	
	public Collection<EmployeeContract> findAllByEmployeeId(Long employeeId);
	
	public Collection<EmployeeContract> findAllByContractId(Long contractId);
	
	public Collection<EmployeeContract> findAllByEmployeeIdIn(Collection<Long> employeeIds);

	public Collection<EmployeeContract> findAllByContractIdIn(Collection<Long> contractIds);

}
