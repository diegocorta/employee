package pm.employee.api.assembler.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;

import es.common.assembler.IAssembler;
import es.common.util.AssemblerUtil;
import es.common.util.JoinEntityMap;
import jakarta.validation.constraints.NotNull;
import pm.employee.api.controller.calendar.WorkshiftController;
import pm.employee.api.controller.calendar.WorkshiftEventController;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.common.dto.calendar.WorkshiftDto;

public class WorkshiftAssembler 
		implements IAssembler<Workshift, WorkshiftDto> {

	private static final WorkshiftAssembler INSTANCE = new WorkshiftAssembler();

    private WorkshiftAssembler() {}

    public static WorkshiftAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public Workshift buildEntityFromDto(WorkshiftDto dto, JoinEntityMap relatedEntities) {
		
		Workshift entity = new Workshift();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public WorkshiftDto buildDtoFromEntity(Workshift entity) {
		
		WorkshiftDto dto = new WorkshiftDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		return dto;
		
	}

	/**
	 * Converts the specified entity representation to the corresponding HATEOAS representation of information
	 *
	 * @param dto complete representation to which we are going to add the HATEOAS links
	 * @return the representation of information created from the specified domain entity
	 */
	@Override
	public EntityModel<WorkshiftDto> buildDtoWithLinksFromEntity(
			@NotNull Workshift entity) {

		WorkshiftDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<WorkshiftDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(WorkshiftController.class).getWorkshift(entity.getId()) )
						.withSelfRel() );
		dtoWithLinks.add(
				linkTo( methodOn(WorkshiftEventController.class).getWorkshiftEvents(entity.getId()) )
						.withRel(WorkshiftDto.EVENTS_REL) );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<WorkshiftDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<Workshift> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}