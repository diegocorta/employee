package pm.employee.api.repository.recording;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.recording.EmployeeJournal;

public interface IEmployeeJournalRepository 
		extends JpaRepository<EmployeeJournal, Long> {
	
	public Collection<EmployeeJournal> findAllByEmployeeId(Long employeeId);
	
	public Optional<EmployeeJournal> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
	
	public Collection<EmployeeJournal> findAllByEmployeeIdAndDateBetween(Long employeeId, LocalDate dateStart, LocalDate dateEnd);
	
	public Collection<EmployeeJournal> findAllByEmployeeIdIn(Collection<Long> employeeIds);

	public Collection<EmployeeJournal> findAllByEmployeeIdInAndDate(Collection<Long> employeeIds, LocalDate date);

	public Collection<EmployeeJournal> findAllByEmployeeIdInAndDateBetween(Collection<Long> employeeIds, LocalDate dateStart, LocalDate dateEnd);

}
