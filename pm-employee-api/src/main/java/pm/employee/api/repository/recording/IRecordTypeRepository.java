package pm.employee.api.repository.recording;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.recording.RecordType;

public interface IRecordTypeRepository 
		extends JpaRepository<RecordType, Long> {

	Optional<RecordType> findFirstByName(String name);
	
	Collection<RecordType> findAllByNameIn(Collection<String> names);
}
