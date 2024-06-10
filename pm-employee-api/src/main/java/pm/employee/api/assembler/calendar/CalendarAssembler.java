package pm.employee.api.assembler.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import pm.employee.api.controller.calendar.CalendarSpecialWorkdayController;
import pm.employee.api.controller.calendar.CalendarStandardWorkdayController;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.common.dto.calendar.CalendarDto;

public class CalendarAssembler 
		implements IAssembler<Calendar, CalendarDto> {

	private static final CalendarAssembler INSTANCE = new CalendarAssembler();

    private CalendarAssembler() {}

    public static CalendarAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public Calendar buildEntityFromDto(CalendarDto dto, JoinEntityMap relatedEntities) {
		
		Calendar entity = new Calendar();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		entity.setStartDate(LocalDate.parse(dto.getStartDate()));
		
		if (StringUtils.hasText(dto.getEndDate())) {
			entity.setEndDate(LocalDate.parse(dto.getEndDate()));
		}
		
		return entity;
		
	}  
    
	@Override
	public CalendarDto buildDtoFromEntity(Calendar entity) {
		
		CalendarDto dto = new CalendarDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

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
	public EntityModel<CalendarDto> buildDtoWithLinksFromEntity(
			@NotNull Calendar entity) {

		CalendarDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<CalendarDto> dtoWithLinks = EntityModel.of(dto);
		
		dtoWithLinks.add(
				linkTo( methodOn(CalendarController.class).getCalendar(entity.getId()) )
						.withSelfRel() );
		dtoWithLinks.add(
				linkTo( methodOn(CalendarSpecialWorkdayController.class).getCalendarSpecialWorkdays(entity.getId()) )
						.withRel("special-workdays") );
		dtoWithLinks.add(
				linkTo( methodOn(CalendarStandardWorkdayController.class).getCalendarStandardWorkdays(entity.getId()) )
						.withRel("standard-workdays") );
		
		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<CalendarDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<Calendar> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}