package pm.employee.api.assembler.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.util.StringUtils;

import es.common.assembler.IAssembler;
import es.common.util.AssemblerUtil;
import es.common.util.JoinEntityMap;
import jakarta.validation.constraints.NotNull;
import pm.employee.api.controller.calendar.CalendarController;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.CalendarSpecialWorkday;
import pm.employee.api.entity.calendar.CalendarStandardWorkday;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;
import pm.employee.common.dto.calendar.CalendarStandardWorkdayDto;

public class CalendarStandardWorkdayAssembler 
		implements IAssembler<CalendarStandardWorkday, CalendarStandardWorkdayDto> {

	private static final CalendarStandardWorkdayAssembler INSTANCE = new CalendarStandardWorkdayAssembler();

    private CalendarStandardWorkdayAssembler() {}

    public static CalendarStandardWorkdayAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public CalendarStandardWorkday buildEntityFromDto(CalendarStandardWorkdayDto dto, JoinEntityMap relatedEntities) {
		
		CalendarStandardWorkday entity = new CalendarStandardWorkday();
		BeanUtils.copyProperties(dto, entity);
		
		Calendar calendar = relatedEntities.get(CalendarSpecialWorkday.CALENDAR, Calendar.class);
		Workday workday = relatedEntities.get(CalendarSpecialWorkday.WORKDAY, Workday.class);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		entity.setCalendar(calendar);
		entity.setWorkday(workday);
		entity.setId(new CalendarWorkdayId(calendar.getId(), workday.getId()));
		
		entity.setWeekdays(dto.getWeekdays()
				.stream()
				.map(DayOfWeek::of)
				.collect(Collectors.toCollection(ArrayList::new)));
		
		entity.setStartDate(LocalDate.parse(dto.getStartDate()));
		
		if (StringUtils.hasText(dto.getEndDate())) {
			entity.setEndDate(LocalDate.parse(dto.getEndDate()));
		}
		
		return entity;
		
	}  
    
	@Override
	public CalendarStandardWorkdayDto buildDtoFromEntity(CalendarStandardWorkday entity) {
		
		CalendarStandardWorkdayDto dto = new CalendarStandardWorkdayDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setCalendarId(entity.getId().getCalendarId());
		dto.setWorkdayId(entity.getId().getWorkdayId());
		
		dto.setWeekdays(entity.getWeekdays()
				.stream()
				.map(DayOfWeek::getValue)
				.toList());
		
		dto.setStartDate(entity.getStartDate().format(DateTimeFormatter.ISO_DATE));

		if (entity.getEndDate() != null) {
			dto.setStartDate(entity.getEndDate().format(DateTimeFormatter.ISO_DATE));
		}
		
		return dto;
		
	}

	/**
	 * Converts the specified entity representation to the corresponding HATEOAS representation of information
	 *
	 * @param dto complete representation to which we are going to add the HATEOAS links
	 * @return the representation of information created from the specified domain entity
	 */
	@Override
	public EntityModel<CalendarStandardWorkdayDto> buildDtoWithLinksFromEntity(
			@NotNull CalendarStandardWorkday entity) {

		CalendarStandardWorkdayDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<CalendarStandardWorkdayDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(CalendarController.class).getCalendar(entity.getCalendar().getId()) )
					.withRel("calendar") );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<CalendarStandardWorkdayDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<CalendarStandardWorkday> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}