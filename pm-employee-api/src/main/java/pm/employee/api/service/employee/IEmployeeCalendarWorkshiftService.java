package pm.employee.api.service.employee;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;
import pm.employee.common.dto.employee.EmployeeCalendarWorkshiftDto;

public interface IEmployeeCalendarWorkshiftService
		extends ICommonService<EmployeeCalendarWorkshiftDto, EmployeeCalendarWorkshiftId> {
	
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByEmployeeId(Long employeeId);
	
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByCalendarId(Long calendarId);
	
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByEmployeeIdIn(Collection<Long> employeeIds);
	
	public Collection<EntityModel<EmployeeCalendarWorkshiftDto>> findAllByCalendarIdIn(Collection<Long> calendarIds);

}