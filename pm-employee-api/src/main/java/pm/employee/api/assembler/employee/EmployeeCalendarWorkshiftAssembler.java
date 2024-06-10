package pm.employee.api.assembler.employee;

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
import pm.employee.api.controller.calendar.WorkshiftController;
import pm.employee.api.controller.employee.EmployeeController;
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.EmployeeCalendarWorkshift;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;
import pm.employee.common.dto.employee.EmployeeCalendarWorkshiftDto;

public class EmployeeCalendarWorkshiftAssembler implements IAssembler<EmployeeCalendarWorkshift, EmployeeCalendarWorkshiftDto>{

	private static final EmployeeCalendarWorkshiftAssembler INSTANCE = new EmployeeCalendarWorkshiftAssembler();

    private EmployeeCalendarWorkshiftAssembler() {}

    public static EmployeeCalendarWorkshiftAssembler getInstance() {
        return INSTANCE;
    }
	
	@Override
	public EmployeeCalendarWorkshift buildEntityFromDto(EmployeeCalendarWorkshiftDto dto, JoinEntityMap relatedEntities) {
		
		EmployeeCalendarWorkshift entity = new EmployeeCalendarWorkshift();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		Employee employee = relatedEntities.get(EmployeeCalendarWorkshift.EMPLOYEE, Employee.class);
		Calendar calendar = relatedEntities.get(EmployeeCalendarWorkshift.CALENDAR, Calendar.class);
		Workshift workshift = relatedEntities.get(EmployeeCalendarWorkshift.WORKSHIFT, Workshift.class);
		
		entity.setEmployee(employee);
		entity.setCalendar(calendar);
		entity.setWorkshift(workshift);
		
		entity.setId(new EmployeeCalendarWorkshiftId(
				employee.getId(), calendar.getId(), workshift.getId()));
		
		entity.setStartDate(LocalDate.parse(dto.getStartDate()));
		
		if (StringUtils.hasText(dto.getEndDate())) {
			entity.setEndDate(LocalDate.parse(dto.getEndDate()));
		}
		
		return entity;
	}

	@Override
	public EmployeeCalendarWorkshiftDto buildDtoFromEntity(EmployeeCalendarWorkshift entity) {
		
		EmployeeCalendarWorkshiftDto dto = new EmployeeCalendarWorkshiftDto();
		BeanUtils.copyProperties(entity, dto);
		
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setEmployeeId(entity.getId().getEmployeeId());
		dto.setCalendarId(entity.getId().getCalendarId());
		dto.setWorkshiftId(entity.getId().getWorkshiftId());
		
		dto.setStartDate(entity.getStartDate().format(DateTimeFormatter.ISO_TIME));
		
		if (entity.getEndDate() != null) {
			dto.setEndDate(entity.getEndDate().format(DateTimeFormatter.ISO_TIME));
		}
		
		return dto;
	}

	@Override
	public EntityModel<EmployeeCalendarWorkshiftDto> buildDtoWithLinksFromEntity(@NotNull EmployeeCalendarWorkshift entity) {
		
		EmployeeCalendarWorkshiftDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<EmployeeCalendarWorkshiftDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(EmployeeController.class).getEmployee(dto.getEmployeeId()) )
						.withRel("employee") );
		dtoWithLinks.add(
				linkTo( methodOn(CalendarController.class).getCalendar(dto.getCalendarId()) )
						.withRel("calendar") );
		dtoWithLinks.add(
				linkTo( methodOn(WorkshiftController.class).getWorkshift(dto.getWorkshiftId()) )
						.withRel("workshift") );

		return dtoWithLinks;
		
	}

	@Override
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<EmployeeCalendarWorkshift> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}

}
