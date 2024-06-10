package pm.employee.api.service.calendar.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.common.service.BasicService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import pm.employee.api.assembler.calendar.CalendarAssembler;
import pm.employee.api.assembler.calendar.WorkshiftAssembler;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.CalendarSpecialWorkday;
import pm.employee.api.entity.calendar.CalendarStandardWorkday;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.repository.calendar.ICalendarRepository;
import pm.employee.api.repository.calendar.ICalendarSpecialWorkdayRepository;
import pm.employee.api.repository.calendar.ICalendarStandardWorkdayRepository;
import pm.employee.api.repository.calendar.IWorkdayWorkshiftRepository;
import pm.employee.api.service.calendar.ICalendarService;
import pm.employee.common.dto.calendar.CalendarDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

@Service
public class CalendarServiceImpl extends BasicService<ICalendarRepository, Calendar, Long, CalendarDto, CalendarAssembler>
		implements ICalendarService {
		
	private final ICalendarSpecialWorkdayRepository calendarSpecialWorkdayRepository;
	private final ICalendarStandardWorkdayRepository calendarStandardWorkdayRepository;
	private final IWorkdayWorkshiftRepository workdayWorkshiftRepository;
	
	private final WorkshiftAssembler workshiftAssembler;
	
	public CalendarServiceImpl(ICalendarRepository repository,
			ICalendarRepository genderTypeRepository,
			ICalendarSpecialWorkdayRepository calendarSpecialWorkdayRepository,
			ICalendarStandardWorkdayRepository calendarStandardWorkdayRepository,
			IWorkdayWorkshiftRepository workdayWorkshiftRepository) {
		
		super(Calendar.class, repository, CalendarAssembler.getInstance());
		
		this.calendarSpecialWorkdayRepository = calendarSpecialWorkdayRepository;
		this.calendarStandardWorkdayRepository = calendarStandardWorkdayRepository;
		this.workdayWorkshiftRepository = workdayWorkshiftRepository;
		
		this.workshiftAssembler = WorkshiftAssembler.getInstance();
	
	}

	@Override
	public Map<CalendarDto, JoinEntityMap> getRelatedEntities(Collection<CalendarDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<CalendarDto> dtos) {
		
		for (CalendarDto dto : dtos) {
			
			Calendar contract = new Calendar();
			contract.setName(dto.getName());
			
			Optional<Calendar> prevContract = repository.findOne(Example.of(contract));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(Calendar.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(Calendar.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent() && !prevContract.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(Calendar.DEFAULT_DESCRIPTION));
			}
		}
	}

	@Override
	public void createDataValidation(Collection<CalendarDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityModel<CalendarDto> findFirstByName(String name) {
		
		return assembler.buildDtoWithLinksFromEntity(
				repository.findFirstByName(name)
					.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(Calendar.DEFAULT_DESCRIPTION))));
	}

	@Override
	public Collection<EntityModel<CalendarDto>> findAllByNameIn(Collection<String> names) {
		
		return repository.findAllByNameIn(names)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public Map<LocalDate, Collection<EntityModel<WorkshiftDto>>> findWorkshiftsByDates(Long id, LocalDate startDate, LocalDate endDate) {
		
		Map<LocalDate, Collection<EntityModel<WorkshiftDto>>> dateWorkshiftsMap = new HashMap<>();
		
		Collection<CalendarStandardWorkday> standardWorkdays = calendarStandardWorkdayRepository.findAllByCalendarIdBetweenDates(id, startDate, endDate);
		Collection<CalendarSpecialWorkday> specialWorkdays = calendarSpecialWorkdayRepository.findAllByCalendarIdBetweenDates(id, startDate, endDate);

		Set<Long> workdays = standardWorkdays.stream()
				.map(csw -> csw.getWorkday().getId())
				.collect(Collectors.toCollection(HashSet::new));
		
		workdays.addAll(specialWorkdays.stream()
				.map(csw -> csw.getWorkday().getId())
				.collect(Collectors.toCollection(HashSet::new)));

		Map<Long, Workshift> workshiftsMap = workdayWorkshiftRepository.findAllWorkshiftOfWorkdayIdIn(workdays).stream()
				.collect(Collectors.toMap(Workshift::getId, Function.identity()));
		
		Map<Long, Set<Workshift>> workshiftsOfWorkdayMap = workdayWorkshiftRepository.findAllByWorkdayIdIn(workdays)
				.stream()
				.collect(Collectors.groupingBy(
						ww -> ww.getWorkday().getId(), 
						Collectors.mapping(ww -> workshiftsMap.get(ww.getWorkshift().getId()), Collectors.toSet())));
		
		int weeksToCheck = (int) (ChronoUnit.DAYS.between(startDate, endDate) / 7) + 1;
		
		for (int week = 1; week <= weeksToCheck; week++) {
		
			LocalDate currentDate = startDate.plusWeeks(week - 1);
	        LocalDate firstDayOfWeek = currentDate.with(ChronoField.DAY_OF_WEEK, 1);
	        
	        for (int weekDay = 1; weekDay <= 7; weekDay++) {
	        	
	        	LocalDate dayOfWeek = firstDayOfWeek.plusDays(weekDay - 1);
	        	
	        	Collection<CalendarStandardWorkday> swe = new HashSet<>();
	        	Collection<CalendarSpecialWorkday> spw = specialWorkdays.stream()
		        		.filter(sw -> sw.getStartDate().compareTo(dayOfWeek) <= 0
	    					&& (sw.getEndDate() == null || sw.getEndDate().compareTo(dayOfWeek) >= 0) )
		        		.collect(Collectors.toList());
		        
	        	if (spw.size() == 0) {
	        		
	        			swe = standardWorkdays.stream()
	    		        		.filter(sw -> sw.getStartDate().compareTo(dayOfWeek) <= 0
		    					&& (sw.getEndDate() == null || sw.getEndDate().compareTo(dayOfWeek) >= 0) 
		    					&& sw.getWeekdays().contains(dayOfWeek.getDayOfWeek()))
			        		.collect(Collectors.toList());
	        	}
	        	
	        	Collection<Workshift> workshift = swe.stream()
	        			.map(s -> s.getWorkday().getId())
	        			.distinct()
	        			.map(workdayId -> workshiftsOfWorkdayMap.get(workdayId))
	        			.flatMap(c -> c.stream()).collect(Collectors.toSet());
		        
	        	workshift.addAll(spw.stream()
	        			.map(s -> s.getWorkday().getId())
	        			.distinct()
	        			.map(workdayId -> workshiftsOfWorkdayMap.get(workdayId))
	        			.flatMap(c -> c.stream()).collect(Collectors.toSet()));
	        	
	        	if (workshift.size() > 0)
	        		dateWorkshiftsMap.put(dayOfWeek, workshiftAssembler.buildDtosWithLinksFromEntities(workshift));
	        }
			
		}

		return dateWorkshiftsMap;
	}
	
}
 