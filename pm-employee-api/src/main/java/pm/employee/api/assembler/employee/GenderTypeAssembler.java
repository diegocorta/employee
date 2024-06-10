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
import pm.employee.api.controller.employee.GenderTypeController;
import pm.employee.api.entity.employee.GenderType;
import pm.employee.common.dto.employee.GenderTypeDto;

public class GenderTypeAssembler 
		implements IAssembler<GenderType, GenderTypeDto> {

	private static final GenderTypeAssembler INSTANCE = new GenderTypeAssembler();

    private GenderTypeAssembler() {}

    public static GenderTypeAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public GenderType buildEntityFromDto(GenderTypeDto dto, JoinEntityMap relatedEntities) {
		
		GenderType entity = new GenderType();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public GenderTypeDto buildDtoFromEntity(GenderType entity) {
		
		GenderTypeDto dto = new GenderTypeDto();
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
	public EntityModel<GenderTypeDto> buildDtoWithLinksFromEntity(
			@NotNull GenderType entity) {

		GenderTypeDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<GenderTypeDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(GenderTypeController.class).getGenderType(entity.getId()) )
						.withSelfRel() );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<GenderTypeDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<GenderType> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}