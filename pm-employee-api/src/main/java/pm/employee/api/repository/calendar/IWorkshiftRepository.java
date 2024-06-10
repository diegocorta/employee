package pm.employee.api.repository.calendar;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.calendar.Workshift;

public interface IWorkshiftRepository 
		extends JpaRepository<Workshift, Long> {

	Optional<Workshift> findFirstByName(String name);
	
	Collection<Workshift> findAllByNameIn(Collection<String> names);
	
}
