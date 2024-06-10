package pm.employee.api.assembler.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;

import es.common.assembler.IAssemblerMinificable;
import es.common.util.AssemblerUtil;
import es.common.util.JoinEntityMap;
import jakarta.validation.constraints.NotNull;
import pm.employee.api.controller.employee.EmployeeContractController;
import pm.employee.api.controller.employee.EmployeeController;
import pm.employee.api.controller.employee.GenderTypeController;
import pm.employee.api.controller.employee.JobTypeController;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.employee.GenderType;
import pm.employee.api.entity.employee.JobType;
import pm.employee.common.dto.employee.EmployeeDto;
import pm.employee.common.dto.employee.min.EmployeeMinDto;

public class EmployeeAssembler 
		implements IAssemblerMinificable<Employee, EmployeeDto, EmployeeMinDto> {

	private static final EmployeeAssembler INSTANCE = new EmployeeAssembler();

    private EmployeeAssembler() {}

    public static EmployeeAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public Employee buildEntityFromDto(EmployeeDto dto, JoinEntityMap relatedEntities) {
		
		Employee entity = new Employee();
		BeanUtils.copyProperties(dto, entity);
		
		GenderType genderType = relatedEntities.get(Employee.GENDER, GenderType.class);
		JobType jobType = relatedEntities.get(Employee.JOB, JobType.class);

		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
		
		entity.setGender(genderType);
		entity.setJob(jobType);
		
		entity.setBirthdate(LocalDate.parse(dto.getBirthdate()));
		
		return entity;
		
	}  
    
	@Override
	public EmployeeDto buildDtoFromEntity(Employee entity) {
		
		EmployeeDto dto = new EmployeeDto();
		BeanUtils.copyProperties(entity, dto);
		
		dto.setGender(entity.getGender().getName());
		dto.setJob(entity.getJob().getName());
		
		dto.setBirthdate(entity.getBirthdate().format(DateTimeFormatter.ISO_DATE));
		
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
	public EntityModel<EmployeeDto> buildDtoWithLinksFromEntity(
			@NotNull Employee entity) {

		EmployeeDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<EmployeeDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(EmployeeController.class).getEmployee(entity.getId()) )
						.withSelfRel() );
		
		dtoWithLinks.add(
				linkTo( methodOn(EmployeeController.class).getEmployeeMinified(entity.getId()) )
						.withRel(EmployeeDto.MINIMIZED_REL) );
		
		dtoWithLinks.add(
				linkTo( methodOn(EmployeeContractController.class).getEmployeeContracts(entity.getId()) )
						.withRel(EmployeeDto.CONTRACTS_REL) );
		
		dtoWithLinks.add(
				linkTo( methodOn(EmployeeContractController.class).getLastEmployeeContracts(entity.getId()) )
						.withRel(EmployeeDto.LAST_CONTRACT_REL) );
		
		dtoWithLinks.add(
				linkTo( methodOn(GenderTypeController.class).getGenderType(entity.getGender().getId()) )
						.withRel(EmployeeDto.GENDER_REL) );
		
		dtoWithLinks.add(
				linkTo( methodOn(JobTypeController.class).getJobType(entity.getJob().getId()) )
						.withRel(EmployeeDto.JOB_REL) );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<EmployeeDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<Employee> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}