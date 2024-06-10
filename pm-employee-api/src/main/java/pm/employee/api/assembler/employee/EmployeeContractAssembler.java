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
import pm.employee.api.controller.employee.EmployeeContractController;
import pm.employee.api.controller.employee.EmployeeController;
import pm.employee.api.entity.employee.ContractType;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.EmployeeContract;
import pm.employee.common.dto.employee.EmployeeContractDto;

public class EmployeeContractAssembler implements IAssembler<EmployeeContract, EmployeeContractDto>{

	private static final EmployeeContractAssembler INSTANCE = new EmployeeContractAssembler();

    private EmployeeContractAssembler() {}

    public static EmployeeContractAssembler getInstance() {
        return INSTANCE;
    }
	
	@Override
	public EmployeeContract buildEntityFromDto(EmployeeContractDto dto, JoinEntityMap relatedEntities) {
		
		EmployeeContract entity = new EmployeeContract();
		BeanUtils.copyProperties(dto, entity);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		ContractType contractType = relatedEntities.get(EmployeeContract.CONTRACT, ContractType.class);
		Employee employee = relatedEntities.get(EmployeeContract.EMPLOYEE, Employee.class);
		
		entity.setContract(contractType);
		entity.setEmployee(employee);
		
		entity.setContractStart(LocalDate.parse(dto.getContractStart()));
		
		if (StringUtils.hasText(dto.getContractEnd())) {
			entity.setContractEnd(LocalDate.parse(dto.getContractEnd()));
		}
		
		return entity;
	}

	@Override
	public EmployeeContractDto buildDtoFromEntity(EmployeeContract entity) {
		
		EmployeeContractDto dto = new EmployeeContractDto();
		BeanUtils.copyProperties(entity, dto);
		
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setEmployeeId(entity.getEmployee().getId());
		dto.setContractTypeId(entity.getContract().getId());
		
		dto.setContractTypeName(entity.getContract().getName());
		
		dto.setContractStart(entity.getContractStart().format(DateTimeFormatter.ISO_DATE));
		
		if (entity.getContractEnd() != null) {
			dto.setContractEnd(entity.getContractEnd().format(DateTimeFormatter.ISO_DATE));
		}
		
		return dto;
	}

	@Override
	public EntityModel<EmployeeContractDto> buildDtoWithLinksFromEntity(@NotNull EmployeeContract entity) {
		
		EmployeeContractDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<EmployeeContractDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(EmployeeContractController.class).getEmployeeContract(dto.getEmployeeId(), dto.getId()) )
						.withSelfRel() );
		dtoWithLinks.add(
				linkTo( methodOn(EmployeeController.class).getEmployee(dto.getEmployeeId()) )
						.withRel("employee") );


		return dtoWithLinks;
		
	}

	@Override
	public Collection<EntityModel<EmployeeContractDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<EmployeeContract> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}

}
