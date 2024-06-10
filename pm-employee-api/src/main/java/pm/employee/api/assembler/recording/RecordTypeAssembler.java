package pm.employee.api.assembler.recording;

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
import pm.employee.api.controller.recording.RecordTypeController;
import pm.employee.api.entity.recording.RecordType;
import pm.employee.common.dto.recording.RecordTypeDto;

public class RecordTypeAssembler 
		implements IAssembler<RecordType, RecordTypeDto> {

	private static final RecordTypeAssembler INSTANCE = new RecordTypeAssembler();

    private RecordTypeAssembler() {}

    public static RecordTypeAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public RecordType buildEntityFromDto(RecordTypeDto dto, JoinEntityMap relatedEntities) {
		
		RecordType entity = new RecordType();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public RecordTypeDto buildDtoFromEntity(RecordType entity) {
		
		RecordTypeDto dto = new RecordTypeDto();
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
	public EntityModel<RecordTypeDto> buildDtoWithLinksFromEntity(
			@NotNull RecordType entity) {

		RecordTypeDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<RecordTypeDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(RecordTypeController.class).getRecordType(entity.getId()) )
						.withSelfRel() );	
		
		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<RecordTypeDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<RecordType> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}