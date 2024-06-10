package pm.employee.api.service.recording.impl;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import org.springframework.util.Assert;

import es.common.service.BasicService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import pm.employee.api.assembler.recording.EmployeeJournalRecordAssembler;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.recording.EmployeeJournal;
import pm.employee.api.entity.recording.EmployeeJournalRecord;
import pm.employee.api.entity.recording.RecordType;
import pm.employee.api.repository.employee.IEmployeeRepository;
import pm.employee.api.repository.recording.IEmployeeJournalRecordRepository;
import pm.employee.api.repository.recording.IEmployeeJournalRepository;
import pm.employee.api.repository.recording.IRecordTypeRepository;
import pm.employee.api.service.recording.IEmployeeJournalRecordService;
import pm.employee.common.dto.recording.EmployeeJournalRecordDto;
import pm.employee.common.dto.recording.EmployeeJournalRecordToRegisterDto;

@Service
public class EmployeeJournalRecordRecordServiceImpl extends BasicService<IEmployeeJournalRecordRepository, EmployeeJournalRecord, Long, EmployeeJournalRecordDto, EmployeeJournalRecordAssembler>
		implements IEmployeeJournalRecordService {
		
	private IEmployeeRepository employeeRepository;
	private IRecordTypeRepository recordTypeRepository;
	private IEmployeeJournalRepository employeeJournalRepository;
	
	public EmployeeJournalRecordRecordServiceImpl(IEmployeeJournalRecordRepository repository,
			IEmployeeRepository employeeRepository,
			IRecordTypeRepository recordTypeRepository,
			IEmployeeJournalRepository employeeJournalRepository) {
		
		super(EmployeeJournalRecord.class, repository, EmployeeJournalRecordAssembler.getInstance());
		
		this.employeeRepository = employeeRepository;
		this.recordTypeRepository = recordTypeRepository;
		this.employeeJournalRepository = employeeJournalRepository;
	
	}

	@Override
	public Map<EmployeeJournalRecordDto, JoinEntityMap> getRelatedEntities(Collection<EmployeeJournalRecordDto> dtos) {
		
		ConcurrentHashMap<EmployeeJournalRecordDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> employeeIds = dtos.parallelStream()
				.map(EmployeeJournalRecordDto::getEmployeeId)
				.distinct()
				.toList();
		
		List<Long> recordTypeIds = dtos.parallelStream()
				.map(EmployeeJournalRecordDto::getRecordTypeId)
				.distinct()
				.toList();
		
		List<Long> employeeJournalIds = dtos.parallelStream()
				.map(EmployeeJournalRecordDto::getEmployeeJournalId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Employee> employeeMap = employeeRepository.findAllById(employeeIds)
				.stream()
				.collect(Collectors.toMap(Employee::getId, Function.identity()));
		
		// Search every entity located
		Map<Long, RecordType> recordTypeMap = recordTypeRepository.findAllById(recordTypeIds)
				.stream()
				.collect(Collectors.toMap(RecordType::getId, Function.identity()));
		
		// Search every entity located
		Map<Long, EmployeeJournal> employeeJournalMap = employeeJournalRepository.findAllById(employeeJournalIds)
				.stream()
				.collect(Collectors.toMap(EmployeeJournal::getId, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			Employee employee = employeeMap.get(dto.getEmployeeId());
			RecordType recordType = recordTypeMap.get(dto.getRecordTypeId());
			EmployeeJournal employeeJournal = employeeJournalMap.get(dto.getEmployeeJournalId());
			
			if (employee == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION));
			
			if (recordType == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(RecordType.DEFAULT_DESCRIPTION));
			
			if (employeeJournal == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(EmployeeJournal.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(EmployeeJournalRecord.EMPLOYEE, employee),
					Pair.of(EmployeeJournalRecord.RECORD_TYPE, recordType),
					Pair.of(EmployeeJournalRecord.EMPLOYEE_JOURNAL, employeeJournal)
					
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
				
	}

	@Override
	public void basicDataValidation(Collection<EmployeeJournalRecordDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createDataValidation(Collection<EmployeeJournalRecordDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdAndEmployeeJournalDate(Long employeeId,
			LocalDate date) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeIdAndEmployeeJournalDate(employeeId, date));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdAndEmployeeJournalDateBetween(
			Long employeeId, LocalDate dateStart, LocalDate dateEnd) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByEmployeeIdAndEmployeeJournalDateBetween(employeeId, dateStart, dateEnd));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdInAndEmployeeJournalDate(
			Collection<Long> employeeIds, LocalDate date) {
		
		return repository.findAllByEmployeeIdInAndEmployeeJournalDate(employeeIds, date)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdInAndEmployeeJournalDateBetween(
			Collection<Long> employeeIds, LocalDate dateStart, LocalDate dateEnd) {
		
		return repository.findAllByEmployeeIdInAndEmployeeJournalDateBetween(employeeIds, dateStart, dateEnd)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeJournalId(Long employeeJournalId) {
		
		return repository.findAllByEmployeeJournalId(employeeJournalId)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	@Transactional
	public EntityModel<EmployeeJournalRecordDto> register(@Valid EmployeeJournalRecordToRegisterDto record) {
		
		EmployeeJournal journal = null;
		EmployeeJournalRecord recordToRegister = new EmployeeJournalRecord();
		
		Employee employee = employeeRepository.findById(record.getEmployeeId())
				.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(Employee.DEFAULT_DESCRIPTION)));
		
		RecordType recordType = recordTypeRepository.findById(record.getRecordTypeId())
				.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(RecordType.DEFAULT_DESCRIPTION)));
		
		if (record.getEmployeeJournalId() != null) {
			
			journal = employeeJournalRepository.findById(record.getEmployeeJournalId())
					.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(EmployeeJournal.DEFAULT_DESCRIPTION)));
			
			if (recordType.isDefaultType() && !record.isEntry()) {
				journal.setJournalEnd(ZonedDateTime.parse(record.getInstant()));
				
				journal = employeeJournalRepository.save(journal);
			}
			
		} else {
			
			var prevJournal = employeeJournalRepository
					.findByEmployeeIdAndDate(record.getEmployeeId(), LocalDate.parse(record.getDate()));
			
			Assert.isTrue(prevJournal.isEmpty(),
					"There cant be a journal of a given user and date if the record does not bring this one");
				
			ZonedDateTime instant = ZonedDateTime.parse(record.getInstant());
			
			EmployeeJournal employeeJournal = new EmployeeJournal();
			employeeJournal.setDate(LocalDate.parse(record.getDate()));
			employeeJournal.setEmployee(employee);
			employeeJournal.setJournalStart(instant);
			employeeJournal.setTimezone(record.getTimezone());
			
			journal = employeeJournalRepository.save(employeeJournal);
		
		}
		
		recordToRegister.setEmployee(employee);
		
		recordToRegister.setEmployeeJournal(journal);
		recordToRegister.setEntry(record.isEntry());
		recordToRegister.setInstant(ZonedDateTime.parse(record.getInstant()));
		recordToRegister.setRecordType(recordType);
		
			
		return assembler.buildDtoWithLinksFromEntity(repository.save(recordToRegister));
	}
}
 