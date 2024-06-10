package pm.employee.api.repository.calendar;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.calendar.Calendar;

public interface ICalendarRepository 
		extends JpaRepository<Calendar, Long> {

	Optional<Calendar> findFirstByName(String name);
	
	Collection<Calendar> findAllByNameIn(Collection<String> names);
	
}
