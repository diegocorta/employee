package pm.employee.api.repository.calendar;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.calendar.WorkshiftEvent;

public interface IWorkshiftEventRepository 
		extends JpaRepository<WorkshiftEvent, Long> {

	Collection<WorkshiftEvent> findAllByWorkshiftId(Long workshiftId);
	
	Collection<WorkshiftEvent> findAllByWorkshiftIdIn(Collection<Long> workshiftIds);
	
}
