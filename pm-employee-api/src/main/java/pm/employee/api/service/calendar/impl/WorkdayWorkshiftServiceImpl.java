package pm.employee.api.service.calendar.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import pm.employee.api.assembler.calendar.WorkdayWorkshiftAssembler;
import pm.employee.api.assembler.calendar.WorkshiftAssembler;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.api.entity.calendar.WorkdayWorkshift;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;
import pm.employee.api.repository.calendar.IWorkdayRepository;
import pm.employee.api.repository.calendar.IWorkdayWorkshiftRepository;
import pm.employee.api.repository.calendar.IWorkshiftRepository;
import pm.employee.api.service.calendar.IWorkdayWorkshiftService;
import pm.employee.common.dto.calendar.WorkdayWorkshiftDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

@Service
public class WorkdayWorkshiftServiceImpl extends BasicService<IWorkdayWorkshiftRepository, WorkdayWorkshift, WorkdayWorkshiftId, WorkdayWorkshiftDto, WorkdayWorkshiftAssembler>
		implements IWorkdayWorkshiftService {
		
	private final IWorkshiftRepository workshiftRepository;
	private final IWorkdayRepository workdayRepository;
	
	private final WorkshiftAssembler workshiftAssembler;
	
	public WorkdayWorkshiftServiceImpl(IWorkdayWorkshiftRepository repository,
			IWorkshiftRepository workshiftRepository,
			IWorkdayRepository workdayRepository) {
		
		super(WorkdayWorkshift.class, repository, WorkdayWorkshiftAssembler.getInstance());
	
		this.workshiftRepository = workshiftRepository;
		this.workdayRepository = workdayRepository;
		
		this.workshiftAssembler = WorkshiftAssembler.getInstance();
	}

	@Override
	public Map<WorkdayWorkshiftDto, JoinEntityMap> getRelatedEntities(Collection<WorkdayWorkshiftDto> dtos) {
		
		ConcurrentHashMap<WorkdayWorkshiftDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> workshiftIds = dtos.parallelStream()
				.map(WorkdayWorkshiftDto::getWorkshiftId)
				.distinct()
				.toList();
		
		List<Long> workdayIds = dtos.parallelStream()
				.map(WorkdayWorkshiftDto::getWorkdayId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Workshift> calendarMap = workshiftRepository.findAllById(workshiftIds)
				.stream()
				.collect(Collectors.toMap(Workshift::getId, Function.identity()));
		
		// Search every entity located
		Map<Long, Workday> workdayMap = workdayRepository.findAllById(workdayIds)
				.stream()
				.collect(Collectors.toMap(Workday::getId, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			Workshift workshift = calendarMap.get(dto.getWorkshiftId());
			Workday workday = workdayMap.get(dto.getWorkdayId());
			
			if (workshift == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Calendar.DEFAULT_DESCRIPTION));
			
			if (workday == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Workday.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(WorkdayWorkshift.WORKSHIFT, workshift),
					Pair.of(WorkdayWorkshift.WORKDAY, workday)
					
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
	}

	@Override
	public void basicDataValidation(Collection<WorkdayWorkshiftDto> dtos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDataValidation(Collection<WorkdayWorkshiftDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkdayId(Long workdayId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByWorkdayId(workdayId));	
	}

	@Override
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkshiftId(Long worksihftId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByWorkshiftId(worksihftId));	
	}

	@Override
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkdayIdIn(Collection<Long> workdayIds) {
		
		return repository.findAllByWorkdayIdIn(workdayIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkshiftIdIn(Collection<Long> workshiftIds) {
		
		return repository.findAllByWorkshiftIdIn(workshiftIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<WorkshiftDto>> findWorkshiftOfWorkday(Long workdayId) {
		
		Set<Long> workshiftIds = repository.findAllByWorkdayId(workdayId)
				.stream()
				.map(gr -> gr.getId().getWorkshiftId())
				.collect(Collectors.toSet());
		
		Set<Workshift> workshifts = workshiftRepository.findAllById(workshiftIds)
				.stream()
				.collect(Collectors.toSet());
		
		return workshiftAssembler.buildDtosWithLinksFromEntities(workshifts);
	}
}
 