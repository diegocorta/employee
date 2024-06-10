package pm.employee.api.assembler.recording;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import pm.employee.api.controller.recording.EmployeeJournalController;
import pm.employee.api.controller.recording.EmployeeJournalRecordController;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.recording.EmployeeJournal;
import pm.employee.common.dto.recording.EmployeeJournalDto;

public class EmployeeJournalAssembler 
		implements IAssembler<EmployeeJournal, EmployeeJournalDto> {

	private static final EmployeeJournalAssembler INSTANCE = new EmployeeJournalAssembler();

    private EmployeeJournalAssembler() {}

    public static EmployeeJournalAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public EmployeeJournal buildEntityFromDto(EmployeeJournalDto dto, JoinEntityMap relatedEntities) {
		
		EmployeeJournal entity = new EmployeeJournal();
		BeanUtils.copyProperties(dto, entity);
		
		Employee employee = relatedEntities.get(EmployeeJournal.EMPLOYEE, Employee.class);
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		entity.setEmployee(employee);
		
		entity.setDate(LocalDate.parse(dto.getDate()));
		entity.setJournalStart(ZonedDateTime.parse(dto.getJournalStart()));
		
		if (StringUtils.hasText(dto.getJournalEnd())) {
			entity.setJournalEnd(ZonedDateTime.parse(dto.getJournalEnd()));
		}

		return entity;
		
	}
	
	@Override
	public EmployeeJournalDto buildDtoFromEntity(EmployeeJournal entity) {
		
		EmployeeJournalDto dto = new EmployeeJournalDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setEmployeeId(entity.getEmployee().getId());
		
		dto.setDate(entity.getDate().format(DateTimeFormatter.ISO_DATE));
		dto.setJournalStart(entity.getJournalStart().format(DateTimeFormatter.ISO_DATE_TIME));

		if (entity.getJournalEnd() != null) {
			dto.setJournalEnd(entity.getJournalEnd().format(DateTimeFormatter.ISO_DATE_TIME));			
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
	public EntityModel<EmployeeJournalDto> buildDtoWithLinksFromEntity(
			@NotNull EmployeeJournal entity) {

		EmployeeJournalDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<EmployeeJournalDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(EmployeeJournalController.class)
						.getEmployeeJournal(entity.getEmployee().getId(), entity.getId()) )
						.withSelfRel() );
		
		dtoWithLinks.add(
				linkTo( methodOn(EmployeeJournalRecordController.class)
						.getEmployeeJournalRecordsOfJournal(entity.getEmployee().getId(), entity.getId()) )
						.withRel(EmployeeJournalDto.RECORDS_REL) );

		return dtoWithLinks;
	}
	
	@Override
	public Collection<EntityModel<EmployeeJournalDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<EmployeeJournal> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}
	
}