package pm.employee.api.assembler.recording;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;

import es.common.assembler.IAssembler;
import es.common.util.AssemblerUtil;
import es.common.util.JoinEntityMap;
import jakarta.validation.constraints.NotNull;
import pm.employee.api.controller.recording.EmployeeJournalController;
import pm.employee.api.controller.recording.RecordTypeController;
import pm.employee.api.entity.employee.Employee;
import pm.employee.api.entity.recording.EmployeeJournal;
import pm.employee.api.entity.recording.EmployeeJournalRecord;
import pm.employee.api.entity.recording.RecordType;
import pm.employee.common.dto.recording.EmployeeJournalRecordDto;

public class EmployeeJournalRecordAssembler 
		implements IAssembler<EmployeeJournalRecord, EmployeeJournalRecordDto> {

	private static final EmployeeJournalRecordAssembler INSTANCE = new EmployeeJournalRecordAssembler();

    private EmployeeJournalRecordAssembler() {}

    public static EmployeeJournalRecordAssembler getInstance() {
        return INSTANCE;
    }

	@Override
	public EmployeeJournalRecord buildEntityFromDto(EmployeeJournalRecordDto dto, JoinEntityMap relatedEntities) {
		
		EmployeeJournalRecord entity = new EmployeeJournalRecord();
		BeanUtils.copyProperties(dto, entity);
		
		Employee employee = relatedEntities.get(EmployeeJournalRecord.EMPLOYEE, Employee.class);
		EmployeeJournal employeeJournal = relatedEntities.get(EmployeeJournalRecord.EMPLOYEE_JOURNAL, EmployeeJournal.class);
		RecordType recordType = relatedEntities.get(EmployeeJournalRecord.RECORD_TYPE, RecordType.class);		
		
		AssemblerUtil.copyBasicPropertiesToEntity(dto, entity);
				
		entity.setEmployee(employee);
		entity.setEmployeeJournal(employeeJournal);
		entity.setRecordType(recordType);
		
		entity.setInstant(ZonedDateTime.parse(dto.getInstant()));
		
		return entity;
		
	}  

	@Override
	public EmployeeJournalRecordDto buildDtoFromEntity(EmployeeJournalRecord entity) {
		
		EmployeeJournalRecordDto dto = new EmployeeJournalRecordDto();
		BeanUtils.copyProperties(entity, dto);
				
		AssemblerUtil.copyBasicPropertiesToDto(entity, dto);

		dto.setEmployeeId(entity.getEmployee().getId());
		dto.setEmployeeJournalId(entity.getEmployeeJournal().getId());
		dto.setRecordTypeId(entity.getRecordType().getId());
		
		dto.setInstant(entity.getInstant().format(DateTimeFormatter.ISO_DATE_TIME));

		
		return dto;
		
	}

	/**
	 * Converts the specified entity representation to the corresponding HATEOAS representation of information
	 *
	 * @param dto complete representation to which we are going to add the HATEOAS links
	 * @return the representation of information created from the specified domain entity
	 */
	@Override
	public EntityModel<EmployeeJournalRecordDto> buildDtoWithLinksFromEntity(
			@NotNull EmployeeJournalRecord entity) {

		EmployeeJournalRecordDto dto = buildDtoFromEntity(entity);
		
		// Add resource relations
		EntityModel<EmployeeJournalRecordDto> dtoWithLinks = EntityModel.of(dto);

		dtoWithLinks.add(
				linkTo( methodOn(EmployeeJournalController.class)
						.getEmployeeJournal(entity.getEmployee().getId(), entity.getId()) )
						.withRel("journal") );
		dtoWithLinks.add(
				linkTo( methodOn(RecordTypeController.class)
						.getRecordType(entity.getRecordType().getId()) )
						.withRel("records") );

		return dtoWithLinks;
	}

	@Override
	public Collection<EntityModel<EmployeeJournalRecordDto>> buildDtosWithLinksFromEntities(
			@NotNull Collection<EmployeeJournalRecord> entities) {
		
		return entities
				.stream()
				.map(entity -> buildDtoWithLinksFromEntity(entity) )
				.collect( Collectors.toList() );
		
	}

}