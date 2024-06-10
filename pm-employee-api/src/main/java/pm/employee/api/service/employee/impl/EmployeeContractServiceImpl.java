package pm.employee.api.service.employee.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import es.common.service.BasicService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityNotFoundException;
import pm.employee.api.assembler.employee.EmployeeContractAssembler;
import pm.employee.api.entity.employee.ContractType;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.EmployeeContract;
import pm.employee.api.repository.employee.IContractTypeRepository;
import pm.employee.api.repository.employee.IEmployeeContractRepository;
import pm.employee.api.repository.employee.IEmployeeRepository;
import pm.employee.api.service.employee.IEmployeeContractService;
import pm.employee.common.dto.employee.EmployeeContractDto;

@Service
public class EmployeeContractServiceImpl extends BasicService<IEmployeeContractRepository, EmployeeContract, Long, EmployeeContractDto, EmployeeContractAssembler>
		implements IEmployeeContractService {
	
	
	private IEmployeeRepository employeeRepository;
	private IContractTypeRepository contractTypeRepository;
	
	public EmployeeContractServiceImpl(IEmployeeContractRepository repository,
			IEmployeeRepository employeeRepository, IContractTypeRepository contractTypeRepository) {
		
		super(EmployeeContract.class, repository, EmployeeContractAssembler.getInstance());
		
		this.employeeRepository = employeeRepository;
		this.contractTypeRepository = contractTypeRepository;
		
	}

	@Override
	public Map<EmployeeContractDto, JoinEntityMap> getRelatedEntities(Collection<EmployeeContractDto> dtos) {
		
		ConcurrentHashMap<EmployeeContractDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> employeeIds = dtos.parallelStream()
				.map(EmployeeContractDto::getEmployeeId)
				.distinct()
				.toList();
		
		List<Long> contractTypeIds = dtos.parallelStream()
				.map(EmployeeContractDto::getContractTypeId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Employee> roleMap = employeeRepository.findAllById(employeeIds)
				.stream()
				.collect(Collectors.toMap(Employee::getId, Function.identity()));
		
		Map<Long, ContractType> groupMap = contractTypeRepository.findAllById(contractTypeIds)
				.stream()
				.collect(Collectors.toMap(ContractType::getId, Function.identity()));
		
		
		dtos.parallelStream().forEach(dto -> {
				
			Employee employee = roleMap.get(dto.getEmployeeId());
			ContractType contract = groupMap.get(dto.getContractTypeId());

			if (employee == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION));
						
			if (contract == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(ContractType.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(EmployeeContract.EMPLOYEE, employee),
					Pair.of(EmployeeContract.CONTRACT, contract)
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
	}

	@Override
	public void basicDataValidation(Collection<EmployeeContractDto> dtos) {
		
	}

	@Override
	public void createDataValidation(Collection<EmployeeContractDto> dtos) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<EntityModel<EmployeeContractDto>> findAllByEmployeeId(Long employeeId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeId(employeeId));
	}
	
	@Override
	public EntityModel<EmployeeContractDto> findLastByEmployeeId(Long employeeId) {
		
		Collection<EmployeeContract> contracts = repository.findAllByEmployeeId(employeeId);
		
		if (contracts.size() == 0) {
			throw new EntityNotFoundException(EmployeeContract.DEFAULT_DESCRIPTION);
		}
		
		EmployeeContract last = contracts.stream()
				.filter(ec -> ec.getContractEnd() == null)
				.findFirst()
				.orElse(null);
		
		if (last == null) {
			last = contracts.stream()
				.sorted(Comparator.comparing(EmployeeContract::getContractEnd).reversed())
				.findFirst().get();
		}
		
		return assembler.buildDtoWithLinksFromEntity(last);
		
	}

	@Override
	public Collection<EntityModel<EmployeeContractDto>> findAllByContractId(Long contractId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByContractId(contractId));
	}

	@Override
	public Collection<EntityModel<EmployeeContractDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds) {
		
		return repository.findAllByEmployeeIdIn(employeeIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeContractDto>> findAllByContractIdIn(Collection<Long> contractIds) {
		
		return repository.findAllByContractIdIn(contractIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
 