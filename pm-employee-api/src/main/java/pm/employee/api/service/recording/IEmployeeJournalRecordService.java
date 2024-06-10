package pm.employee.api.service.recording;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import jakarta.validation.Valid;
import pm.employee.common.dto.recording.EmployeeJournalRecordDto;
import pm.employee.common.dto.recording.EmployeeJournalRecordToRegisterDto;

public interface IEmployeeJournalRecordService extends ICommonService<EmployeeJournalRecordDto, Long> {

	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeJournalId(Long employeeJournalId);
	
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdAndEmployeeJournalDate(Long employeeId, LocalDate date);
	
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdAndEmployeeJournalDateBetween(Long employeeId, LocalDate dateStart, LocalDate dateEnd);
	
	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdInAndEmployeeJournalDate(Collection<Long> employeeIds, LocalDate date);

	public Collection<EntityModel<EmployeeJournalRecordDto>> findAllByEmployeeIdInAndEmployeeJournalDateBetween(Collection<Long> employeeIds, LocalDate dateStart, LocalDate dateEnd);

	public EntityModel<EmployeeJournalRecordDto> register(@Valid EmployeeJournalRecordToRegisterDto ejrtr);

	
}