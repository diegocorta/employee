package pm.employee.api.repository.employee;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.employee.GenderType;

public interface IGenderTypeRepository 
		extends JpaRepository<GenderType, Long> {

	Optional<GenderType> findFirstByName(String name);
	
	Collection<GenderType> findAllByNameIn(Collection<String> names);
}
