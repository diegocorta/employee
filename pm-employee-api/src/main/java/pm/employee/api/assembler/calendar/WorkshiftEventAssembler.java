package pm.employee.api.assembler.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalTime;
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
import pm.employee.api.controller.calendar.WorkshiftEventController;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.calendar.WorkshiftEvent;
import pm.employee.common.dto.calendar.WorkshiftEventDto;

public class WorkshiftEventAssembler 
		implements IAssembler<WorkshiftEvent, WorkshiftEventDto> {

	private static final WorkshiftEventAssembler INSTANCE = new WorkshiftEventAssembler();

    private WorkshiftEventAssembler() {}

    public static WorkshiftEventAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public WorkshiftEvent buildEntityFromDto(WorkshiftEventDto dto, JoinEntityMap relatedEntities) {
		
		WorkshiftEvent entity = new WorkshiftEvent();
		BeanUtils.copyProperties(dto, entity);
		
		Workshift workshift = relatedEntities.get(WorkshiftEvent.WORKSHIFT, Workshift.class);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		entity.setWorkshift(workshift);

		if (StringUtils.hasText(dto.getTime())) {
			entity.setTime(LocalTime.parse(dto.getTime()));
		}
		
		return entity;
		
	}  
    
	@Override
	public WorkshiftEventDto buildDtoFromEntity(WorkshiftEvent entity) {
		
		WorkshiftEventDto dto = new WorkshiftEventDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setWorkshiftId(entity.getWorkshift().getId());
		
		if (entity.getTime() != null) {
			dto.setTime(entity.getTime().format(DateTimeFormatter.ISO_TIME));
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
	public EntityModel<WorkshiftEventDto> buildDtoWithLinksFromEntity(
			@NotNull WorkshiftEvent entity) {

		WorkshiftEventDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<WorkshiftEventDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(WorkshiftEventController.class).getOneWorkshiftEvents(entity.getWorkshift().getId(), entity.getId()) )
						.withSelfRel() );
		
		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<WorkshiftEventDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<WorkshiftEvent> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}