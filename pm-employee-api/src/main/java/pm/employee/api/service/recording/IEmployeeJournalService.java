package pm.employee.api.service.recording;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.recording.EmployeeJournalDto;

public interface IEmployeeJournalService extends ICommonService<EmployeeJournalDto, Long> {

	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeId(Long employeeId);
		
	public EntityModel<EmployeeJournalDto> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
	
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdAndDateBetween(Long employeeId, LocalDate dateStart, LocalDate dateEnd);
	
	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds);

	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdInAndDate(Collection<Long> employeeIds, LocalDate date);

	public Collection<EntityModel<EmployeeJournalDto>> findAllByEmployeeIdInAndDateBetween(Collection<Long> employeeIds, LocalDate dateStart, LocalDate dateEnd);
	
}