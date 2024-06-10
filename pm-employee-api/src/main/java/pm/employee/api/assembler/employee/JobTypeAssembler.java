package pm.employee.api.assembler.employee;

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
import pm.employee.api.controller.employee.JobTypeController;
import pm.employee.api.entity.employee.JobType;
import pm.employee.common.dto.employee.JobTypeDto;

public class JobTypeAssembler 
		implements IAssembler<JobType, JobTypeDto> {

	private static final JobTypeAssembler INSTANCE = new JobTypeAssembler();

    private JobTypeAssembler() {}

    public static JobTypeAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public JobType buildEntityFromDto(JobTypeDto dto, JoinEntityMap relatedEntities) {
		
		JobType entity = new JobType();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public JobTypeDto buildDtoFromEntity(JobType entity) {
		
		JobTypeDto dto = new JobTypeDto();
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
	public EntityModel<JobTypeDto> buildDtoWithLinksFromEntity(
			@NotNull JobType entity) {

		JobTypeDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<JobTypeDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(JobTypeController.class).getJobType(entity.getId()) )
						.withSelfRel() );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<JobTypeDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<JobType> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}