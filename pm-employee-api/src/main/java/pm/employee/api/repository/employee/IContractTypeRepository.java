package pm.employee.api.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.ContractType;

public interface IContractTypeRepository 
		extends JpaRepository<ContractType, Long> {

}
