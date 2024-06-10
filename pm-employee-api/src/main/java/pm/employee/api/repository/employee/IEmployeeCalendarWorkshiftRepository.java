package pm.employee.api.repository.employee;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.EmployeeCalendarWorkshift;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;

public interface IEmployeeCalendarWorkshiftRepository 
		extends JpaRepository<EmployeeCalendarWorkshift, EmployeeCalendarWorkshiftId> {
	
	public Collection<EmployeeCalendarWorkshift> findAllByEmployeeId(Long employeeId);
	
	public Collection<EmployeeCalendarWorkshift> findAllByCalendarId(Long calendarId);
	
	public Collection<EmployeeCalendarWorkshift> findAllByEmployeeIdIn(Collection<Long> employeeIds);

	public Collection<EmployeeCalendarWorkshift> findAllByCalendarIdIn(Collection<Long> calendarIds);

}
