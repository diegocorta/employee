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
import pm.employee.api.assembler.calendar.CalendarStandardWorkdayAssembler;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.CalendarStandardWorkday;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;
import pm.employee.api.repository.calendar.ICalendarRepository;
import pm.employee.api.repository.calendar.ICalendarStandardWorkdayRepository;
import pm.employee.api.repository.calendar.IWorkdayRepository;
import pm.employee.api.service.calendar.ICalendarStandardWorkdayService;
import pm.employee.common.dto.calendar.CalendarStandardWorkdayDto;

@Service
public class CalendarStandardWorkdayServiceImpl extends BasicService<ICalendarStandardWorkdayRepository, CalendarStandardWorkday, CalendarWorkdayId, CalendarStandardWorkdayDto, CalendarStandardWorkdayAssembler>
		implements ICalendarStandardWorkdayService {
		
	private ICalendarRepository calendarRepository;
	private IWorkdayRepository workdayRepository;
	
	public CalendarStandardWorkdayServiceImpl(ICalendarStandardWorkdayRepository repository,
			ICalendarRepository calendarRepository,
			IWorkdayRepository workdayRepository) {
		
		super(CalendarStandardWorkday.class, repository, CalendarStandardWorkdayAssembler.getInstance());
	
		this.calendarRepository = calendarRepository;
		this.workdayRepository = workdayRepository;
	}

	@Override
	public Map<CalendarStandardWorkdayDto, JoinEntityMap> getRelatedEntities(Collection<CalendarStandardWorkdayDto> dtos) {
		
		ConcurrentHashMap<CalendarStandardWorkdayDto, JoinEntityMap> result = new ConcurrentHashMap<>();
		
		// Obtaining all the identifier of users and groups
		List<Long> calendarIds = dtos.parallelStream()
				.map(CalendarStandardWorkdayDto::getCalendarId)
				.distinct()
				.toList();
		
		List<Long> workdayIds = dtos.parallelStream()
				.map(CalendarStandardWorkdayDto::getWorkdayId)
				.distinct()
				.toList();
		
		// Search every entity located
		Map<Long, Calendar> calendarMap = calendarRepository.findAllById(calendarIds)
				.stream()
				.collect(Collectors.toMap(Calendar::getId, Function.identity()));
		
		// Search every entity located
		Map<Long, Workday> workdayMap = workdayRepository.findAllById(workdayIds)
				.stream()
				.collect(Collectors.toMap(Workday::getId, Function.identity()));
		
		dtos.parallelStream().forEach(dto -> {
				
			Calendar calendar = calendarMap.get(dto.getCalendarId());
			Workday workday = workdayMap.get(dto.getWorkdayId());
			
			if (calendar == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Calendar.DEFAULT_DESCRIPTION));
			
			if (workday == null) throw new EntityNotFoundException(
					MessageUtils.entityNotFoundExceptionMessage(Workday.DEFAULT_DESCRIPTION));
			
			Collection<Pair<String, Object>> joinEntityList = List.of(
					Pair.of(CalendarStandardWorkday.CALENDAR, calendar),
					Pair.of(CalendarStandardWorkday.WORKDAY, workday)
					
			);

			result.put(dto, JoinEntityMap.from(joinEntityList));
			
		});
		
		return result;
	}

	@Override
	public void basicDataValidation(Collection<CalendarStandardWorkdayDto> dtos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createDataValidation(Collection<CalendarStandardWorkdayDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByWorkdayId(Long workdayId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByWorkdayId(workdayId));
	}

	@Override
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByCalendarId(Long calendarId) {
		
		return assembler.buildDtosWithLinksFromEntities(
				repository.findAllByCalendarId(calendarId));
	}

	@Override
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByWorkdayIdIn(Collection<Long> workdayIds) {
		
		return repository.findAllByWorkdayIdIn(workdayIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByCalendarIdIn(Collection<Long> calendarIds) {
		
		return repository.findAllByCalendarIdIn(calendarIds)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
 