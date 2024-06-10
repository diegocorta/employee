package pm.employee.api.service.calendar.impl;

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
import pm.employee.api.assembler.calendar.WorkshiftEventAssembler;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.calendar.WorkshiftEvent;
import pm.employee.api.repository.calendar.IWorkshiftEventRepository;
import pm.employee.api.repository.calendar.IWorkshiftRepository;
import pm.employee.api.service.calendar.IWorkshiftEventService;
import pm.employee.common.dto.calendar.WorkshiftEventDto;

@Service
public class WorkshiftEventServiceImpl extends BasicService<IWorkshiftEventRepository, WorkshiftEvent, Long, WorkshiftEventDto, WorkshiftEventAssembler>
		implements IWorkshiftEventService {
		
	private IWorkshiftRepository workshiftRepository;
	
	public WorkshiftEventServiceImpl(IWorkshiftEventRepository repository,
			IWorkshiftRepository workshiftRepository) {
		
		super(WorkshiftEvent.class, repository, WorkshiftEventAssembler.getInstance());
	
		this.workshiftRepository = workshiftRepository;
	}

	@Override
	public Map<WorkshiftEventDto, JoinEntityMap> getRelatedEntities(Collection<WorkshiftEventDto> dtos) {
		
		ConcurrentHashMap<WorkshiftEventDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		List<Long> workshiftIds = dtos.parallelStream()
				.map(WorkshiftEventDto::getWorkshiftId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Workshift> workshiftMap = workshiftRepository.findAllById(workshiftIds)
				.stream()
				.collect(Collectors.toMap(Workshift::getId, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			Workshift workshift = workshiftMap.get(dto.getWorkshiftId());
			
			if (workshift == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Workshift.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(WorkshiftEvent.WORKSHIFT, workshift)
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
	}

	@Override
	public void basicDataValidation(Collection<WorkshiftEventDto> dtos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDataValidation(Collection<WorkshiftEventDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<EntityModel<WorkshiftEventDto>> findAllByWorkshiftId(Long workshiftId) {
		
		return repository.findAllByWorkshiftId(workshiftId)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<WorkshiftEventDto>> findAllByWorkshiftIdIn(Collection<Long> workshiftIds) {
		
		return repository.findAllByWorkshiftIdIn(workshiftIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
 