package pm.employee.api.service.employee.impl;

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
import pm.employee.api.assembler.employee.EmployeeCalendarWorkshiftAssembler;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.EmployeeCalendarWorkshift;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;
import pm.employee.api.repository.calendar.ICalendarRepository;
import pm.employee.api.repository.calendar.IWorkshiftRepository;
import pm.employee.api.repository.employee.IEmployeeCalendarWorkshiftRepository;
import pm.employee.api.repository.employee.IEmployeeRepository;
import pm.employee.api.service.employee.IEmployeeCalendarWorkshiftService;
import pm.employee.common.dto.employee.EmployeeCalendarWorkshiftDto;

@Service
public class EmployeeCalendarWorkshiftServiceImpl extends BasicService<IEmployeeCalendarWorkshiftRepository, EmployeeCalendarWorkshift, EmployeeCalendarWorkshiftId, EmployeeCalendarWorkshiftDto, EmployeeCalendarWorkshiftAssembler>
		implements IEmployeeCalendarWorkshiftService {
	
	
	private IEmployeeRepository employeeRepository;
	private ICalendarRepository calendarRepository;
	private IWorkshiftRepository workshiftRepository;
	
	public EmployeeCalendarWorkshiftServiceImpl(IEmployeeCalendarWorkshiftRepository repository,
			IEmployeeRepository employeeRepository,ICalendarRepository calendarRepository, IWorkshiftRepository workshiftRepository) {
		
		super(EmployeeCalendarWorkshift.class, repository, EmployeeCalendarWorkshiftAssembler.getInstance());
		
		this.employeeRepository = employeeRepository;
		this.calendarRepository = calendarRepository;
		this.workshiftRepository = workshiftRepository;
		
	}

	@Override
	public Map<EmployeeCalendarWorkshiftDto, JoinEntityMap> getRelatedEntities(Collection<EmployeeCalendarWorkshiftDto> dtos) {
		
		ConcurrentHashMap<EmployeeCalendarWorkshiftDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> employeeIds = dtos.parallelStream()
				.map(EmployeeCalendarWorkshiftDto::getEmployeeId)
				.distinct()
				.toList();
		
		List<Long> calendarIds = dtos.parallelStream()
				.map(EmployeeCalendarWorkshiftDto::getCalendarId)
				.distinct()
				.toList();
		
		List<Long> workshiftIds = dtos.parallelStream()
				.map(EmployeeCalendarWorkshiftDto::getWorkshiftId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Employee> employeeMap = employeeRepository.findAllById(employeeIds)
				.stream()
				.collect(Collectors.toMap(Employee::getId, Function.identity()));
		
		Map<Long, Calendar> calendarMap = calendarRepository.findAllById(calendarIds)
				.stream()
				.collect(Collectors.toMap(Calendar::getId, Function.identity()));
		
		Map<Long, Workshift> workshiftMap = workshiftRepository.findAllById(workshiftIds)
				.stream()
				.collect(Collectors.toMap(Workshift::getId, Function.identity()));
		
		
		dtos.parallelStream().forEach(dto -> {
				
			Employee employee = employeeMap.get(dto.getEmployeeId());
			Calendar calendar = calendarMap.get(dto.getCalendarId());
			Workshift workshift = workshiftMap.get(dto.getWorkshiftId());

			
			if (employee == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION));
						
			if (calendar == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Calendar.DEFAULT_DESCRIPTION));
			
			if (workshift == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Workshift.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(EmployeeCalendarWorkshift.EMPLOYEE, employee),
					Pair.of(EmployeeCalendarWorkshift.CALENDAR, calendar),
					Pair.of(EmployeeCalendarWorkshift.WORKSHIFT, workshift)
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
	}

	@Override
	public void basicDataValidation(Collection<EmployeeCalendarWorkshiftDto> dtos) {
		
	}

	@Override
	public void createDataValidation(Collection<EmployeeCalendarWorkshiftDto> dtos) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByEmployeeId(Long employeeId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeId(employeeId));
	}

	@Override
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByCalendarId(Long calendarId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByCalendarId(calendarId));
	}

	@Override
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds) {
		
		return repository.findAllByEmployeeIdIn(employeeIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByCalendarIdIn(Collection<Long> calendarIds) {
		
		return repository.findAllByCalendarIdIn(calendarIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
 