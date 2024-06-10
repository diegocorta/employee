package pm.employee.api.service.recording.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import pm.employee.api.assembler.recording.EmployeeJournalAssembler;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.recording.EmployeeJournal;
import pm.employee.api.repository.employee.IEmployeeRepository;
import pm.employee.api.repository.recording.IEmployeeJournalRepository;
import pm.employee.api.service.recording.IEmployeeJournalService;
import pm.employee.common.dto.recording.EmployeeJournalDto;

@Service
public class EmployeeJournalServiceImpl extends BasicService<IEmployeeJournalRepository, EmployeeJournal, Long, EmployeeJournalDto, EmployeeJournalAssembler>
		implements IEmployeeJournalService {
		
	private IEmployeeRepository employeeRepository;
	
	public EmployeeJournalServiceImpl(IEmployeeJournalRepository repository,
			IEmployeeJournalRepository genderTypeRepository,
			IEmployeeRepository employeeRepository) {
		
		super(EmployeeJournal.class, repository, EmployeeJournalAssembler.getInstance());
		
		this.employeeRepository = employeeRepository;
	
	}

	@Override
	public Map<EmployeeJournalDto, JoinEntityMap> getRelatedEntities(Collection<EmployeeJournalDto> dtos) {
		
		ConcurrentHashMap<EmployeeJournalDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> employeeIds = dtos.parallelStream()
				.map(EmployeeJournalDto::getEmployeeId)
				.distinct()
				.toList();

		
		// Search every entity located
		Map<Long, Employee> employeeMap = employeeRepository.findAllById(employeeIds)
				.stream()
				.collect(Collectors.toMap(Employee::getId, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			Employee employee = employeeMap.get(dto.getEmployeeId());

			if (employee == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(EmployeeJournal.EMPLOYEE, employee)
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
				
	}

	@Override
	public void basicDataValidation(Collection<EmployeeJournalDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createDataValidation(Collection<EmployeeJournalDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeId(Long employeeId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeId(employeeId));
	}

	@Override
	public EntityModel<EmployeeJournalDto> findByEmployeeIdAndDate(Long employeeId, LocalDate date) {
		
		return assembler.buildDtoWithLinksFromEntity(
				repository.findByEmployeeIdAndDate(employeeId, date).orElseThrow(
						() -> new EntityNotFoundException(EmployeeJournal.DEFAULT_DESCRIPTION)));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdAndDateBetween(Long employeeId,
			LocalDate dateStart, LocalDate dateEnd) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeIdAndDateBetween(employeeId, dateStart, dateEnd));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds) {
		
		return repository.findAllByEmployeeIdIn(employeeIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdInAndDate(Collection<Long> employeeIds,
			LocalDate date) {
		
		return repository.findAllByEmployeeIdInAndDate(employeeIds, date)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdInAndDateBetween(Collection<Long> employeeIds,
			LocalDate dateStart, LocalDate dateEnd) {
		
		return repository.findAllByEmployeeIdInAndDateBetween(employeeIds, dateStart, dateEnd)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}
 