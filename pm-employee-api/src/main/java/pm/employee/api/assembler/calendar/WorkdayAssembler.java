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
import pm.employee.api.controller.calendar.WorkdayController;
import pm.employee.api.controller.calendar.WorkdayWorkshiftController;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.common.dto.calendar.WorkdayDto;

public class WorkdayAssembler 
		implements IAssembler<Workday, WorkdayDto> {

	private static final WorkdayAssembler INSTANCE = new WorkdayAssembler();

    private WorkdayAssembler() {}

    public static WorkdayAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public Workday buildEntityFromDto(WorkdayDto dto, JoinEntityMap relatedEntities) {
		
		Workday entity = new Workday();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public WorkdayDto buildDtoFromEntity(Workday entity) {
		
		WorkdayDto dto = new WorkdayDto();
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
	public EntityModel<WorkdayDto> buildDtoWithLinksFromEntity(
			@NotNull Workday entity) {

		WorkdayDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<WorkdayDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(WorkdayController.class).getWorkday(entity.getId()) )
						.withSelfRel() );
		dtoWithLinks.add(
				linkTo( methodOn(WorkdayWorkshiftController.class).getWorkdaysOfWorkshift(entity.getId()) )
						.withRel(WorkdayDto.WORKSHIFT_REL) );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<WorkdayDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<Workday> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}