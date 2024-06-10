package pm.employee.api.repository.recording;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pm.employee.api.entity.recording.EmployeeJournalRecord;

public interface IEmployeeJournalRecordRepository 
		extends JpaRepository<EmployeeJournalRecord, Long> {

	public Collection<EmployeeJournalRecord> findAllByEmployeeJournalId(Long employeeJournalId);
	
	public Collection<EmployeeJournalRecord> findAllByEmployeeIdAndEmployeeJournalDate(Long calendarId, LocalDate date);
	
	public Collection<EmployeeJournalRecord> findAllByEmployeeIdAndEmployeeJournalDateBetween(Long calendarId, LocalDate dateStart, LocalDate dateEnd);
	
	public Collection<EmployeeJournalRecord> findAllByEmployeeIdInAndEmployeeJournalDate(Collection<Long> calendarIds, LocalDate date);

	public Collection<EmployeeJournalRecord> findAllByEmployeeIdInAndEmployeeJournalDateBetween(Collection<Long> calendarIds, LocalDate dateStart, LocalDate dateEnd);

}
