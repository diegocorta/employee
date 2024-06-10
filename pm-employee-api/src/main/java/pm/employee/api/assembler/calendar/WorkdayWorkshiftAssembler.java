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
import pm.employee.api.controller.calendar.WorkshiftController;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.api.entity.calendar.WorkdayWorkshift;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;
import pm.employee.common.dto.calendar.WorkdayWorkshiftDto;

public class WorkdayWorkshiftAssembler 
		implements IAssembler<WorkdayWorkshift, WorkdayWorkshiftDto> {

	private static final WorkdayWorkshiftAssembler INSTANCE = new WorkdayWorkshiftAssembler();

    private WorkdayWorkshiftAssembler() {}

    public static WorkdayWorkshiftAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public WorkdayWorkshift buildEntityFromDto(WorkdayWorkshiftDto dto, JoinEntityMap relatedEntities) {
		
		WorkdayWorkshift entity = new WorkdayWorkshift();
		BeanUtils.copyProperties(dto, entity);
		
		Workshift workshift = relatedEntities.get(WorkdayWorkshift.WORKSHIFT, Workshift.class);
		Workday workday = relatedEntities.get(WorkdayWorkshift.WORKDAY, Workday.class);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		entity.setWorkshift(workshift);
		entity.setWorkday(workday);
		entity.setId(new WorkdayWorkshiftId(workday.getId(), workshift.getId()));
		
		return entity;
		
	}  
    
	@Override
	public WorkdayWorkshiftDto buildDtoFromEntity(WorkdayWorkshift entity) {
		
		WorkdayWorkshiftDto dto = new WorkdayWorkshiftDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);
		
		dto.setWorkdayId(entity.getId().getWorkdayId());
		dto.setWorkshiftId(entity.getId().getWorkshiftId());
		
		return dto;
		
	}

	/**
	 * Converts the specified entity representation to the corresponding HATEOAS representation of information
	 *
	 * @param dto complete representation to which we are going to add the HATEOAS links
	 * @return the representation of information created from the specified domain entity
	 */
	@Override
	public EntityModel<WorkdayWorkshiftDto> buildDtoWithLinksFromEntity(
			@NotNull WorkdayWorkshift entity) {

		WorkdayWorkshiftDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<WorkdayWorkshiftDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(WorkdayController.class).getWorkday(entity.getWorkday().getId()) )
						.withRel("workday") );
		dtoWithLinks.add(
				linkTo( methodOn(WorkshiftController.class).getWorkshift(entity.getWorkshift().getId()) )
						.withRel("workshift") );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<WorkdayWorkshiftDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<WorkdayWorkshift> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}