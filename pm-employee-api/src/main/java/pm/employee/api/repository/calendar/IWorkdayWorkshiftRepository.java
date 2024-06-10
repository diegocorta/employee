package pm.employee.api.repository.calendar;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pm.employee.api.entity.calendar.WorkdayWorkshift;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;

public interface IWorkdayWorkshiftRepository 
		extends JpaRepository<WorkdayWorkshift, WorkdayWorkshiftId> {
	
	public Collection<WorkdayWorkshift> findAllByWorkdayId(Long workdayId);
	
	public Collection<WorkdayWorkshift> findAllByWorkshiftId(Long worksihftId);
	
	public Collection<WorkdayWorkshift> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<WorkdayWorkshift> findAllByWorkshiftIdIn(Collection<Long> workshiftIds);

	@Query("select ws from WorkdayWorkshift ww "
			+ "inner join ww.workshift ws "
			+ "inner join ww.workday wd "
			+ "where wd.id in :workdayIds ")
	public Collection<Workshift> findAllWorkshiftOfWorkdayIdIn(Collection<Long> workdayIds);
	
}
