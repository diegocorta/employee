package pm.employee.api.service.employee.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.common.service.BasicMinificableService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import pm.employee.api.assembler.employee.EmployeeAssembler;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.GenderType;
import pm.employee.api.entity.employee.JobType;
import pm.employee.api.repository.employee.IEmployeeRepository;
import pm.employee.api.repository.employee.IGenderTypeRepository;
import pm.employee.api.repository.employee.IJobTypeRepository;
import pm.employee.api.service.employee.IEmployeeService;
import pm.employee.common.dto.employee.EmployeeDto;
import pm.employee.common.dto.employee.min.EmployeeMinDto;

@Service
public class EmployeeServiceImpl extends BasicMinificableService<IEmployeeRepository, Employee, Long, EmployeeDto, EmployeeMinDto, EmployeeAssembler>
		implements IEmployeeService {
	
	private IGenderTypeRepository genderTypeRepository;
	private IJobTypeRepository jobTypeRepository;

	
	public EmployeeServiceImpl(IEmployeeRepository repository,
			IGenderTypeRepository genderTypeRepository,
			IJobTypeRepository jobTypeRepository) {
		
		super(Employee.class, EmployeeMinDto.class, repository, EmployeeAssembler.getInstance());
	
		this.genderTypeRepository = genderTypeRepository;
		this.jobTypeRepository = jobTypeRepository;
	}

	@Override
	public Map<EmployeeDto, JoinEntityMap> getRelatedEntities(Collection<EmployeeDto> dtos) {
		
		ConcurrentHashMap<EmployeeDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<String> names = dtos.parallelStream()
				.map(EmployeeDto::getGender)
				.distinct()
				.toList();
		
		// Obtaining all the identifier of users and groups
		List<String> jobNames = dtos.parallelStream()
				.map(EmployeeDto::getJob)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<String, GenderType> genderMap = genderTypeRepository.findAllByNameIn(names)
				.stream()
				.collect(Collectors.toMap(GenderType::getName, Function.identity()));
		
		// Search every entity located
		Map<String, JobType> jobMap = jobTypeRepository.findAllByNameIn(jobNames)
				.stream()
				.collect(Collectors.toMap(JobType::getName, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			GenderType gender = genderMap.get(dto.getGender());

			if (gender == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(GenderType.DEFAULT_DESCRIPTION));
			
			JobType job = jobMap.get(dto.getJob());

			if (job == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(JobType.DEFAULT_DESCRIPTION));
						
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(Employee.GENDER, gender),
					Pair.of(Employee.JOB, job)
					
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;	
	}

	@Override
	public void basicDataValidation(Collection<EmployeeDto> dtos) {
		
		for (EmployeeDto dto : dtos) {
			
			Employee employee = new Employee();
			employee.setCardId(dto.getCardId());
			
			Optional<Employee> prevUser = repository.findOne(Example.of(employee));
			boolean cardIdDuplicated = false;
			
			if (cardIdDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(Employee.DEFAULT_DESCRIPTION));
				if (prevUser.isPresent()) cardIdDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(Employee.DEFAULT_DESCRIPTION));
				if (prevUser.isPresent() && !prevUser.get().getId().equals(dto.getId()))
					cardIdDuplicated = true;
				
			}
			
			if (cardIdDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(Employee.DEFAULT_DESCRIPTION));
			}
		}
		
	}

	@Override
	public void createDataValidation(Collection<EmployeeDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityModel<EmployeeDto> findEmployeeBySecurityUser(Long securityUserId) {
				
		Employee employee = repository.findOneBysecurityUserId(securityUserId).orElseThrow(() ->  
		new EntityNotFoundException(
				MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION)));
		
		return assembler.buildDtoWithLinksFromEntity(employee);
	}
	
}
 