package pm.employee.api.repository.calendar;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.calendar.Workday;

public interface IWorkdayRepository 
		extends JpaRepository<Workday, Long> {

	Optional<Workday> findFirstByName(String name);
	
	Collection<Workday> findAllByNameIn(Collection<String> names);
	
}
