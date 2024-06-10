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
import pm.employee.api.controller.employee.ContractTypeController;
import pm.employee.api.entity.employee.ContractType;
import pm.employee.common.dto.employee.ContractTypeDto;

public class ContractTypeAssembler 
		implements IAssembler<ContractType, ContractTypeDto> {

	private static final ContractTypeAssembler INSTANCE = new ContractTypeAssembler();

    private ContractTypeAssembler() {}

    public static ContractTypeAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public ContractType buildEntityFromDto(ContractTypeDto dto, JoinEntityMap relatedEntities) {
		
		ContractType entity = new ContractType();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		return entity;
		
	}  
    
	@Override
	public ContractTypeDto buildDtoFromEntity(ContractType entity) {
		
		ContractTypeDto dto = new ContractTypeDto();
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
	public EntityModel<ContractTypeDto> buildDtoWithLinksFromEntity(
			@NotNull ContractType entity) {

		ContractTypeDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<ContractTypeDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(ContractTypeController.class).getContractType(entity.getId()) )
						.withSelfRel() );


		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<ContractTypeDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<ContractType> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}