package pm.employee.api.repository.employee;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.JobType;

public interface IJobTypeRepository 
		extends JpaRepository<JobType, Long> {

	Optional<JobType> findFirstByName(String name);
	
	Collection<JobType> findAllByNameIn(Collection<String> names);
}
