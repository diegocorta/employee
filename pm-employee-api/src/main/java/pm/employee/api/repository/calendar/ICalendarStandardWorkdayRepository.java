package pm.employee.api.repository.calendar;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pm.employee.api.entity.calendar.CalendarStandardWorkday;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;

public interface ICalendarStandardWorkdayRepository 
		extends JpaRepository<CalendarStandardWorkday, CalendarWorkdayId> {
	
	public Collection<CalendarStandardWorkday> findAllByWorkdayId(Long workdayId);
	
	public Collection<CalendarStandardWorkday> findAllByCalendarId(Long calendarId);
	
	public Collection<CalendarStandardWorkday> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<CalendarStandardWorkday> findAllByCalendarIdIn(Collection<Long> calendarIds);
		
	@Query("select csw from CalendarStandardWorkday csw "
			+ "inner join csw.calendar c "
			+ "where c.id = :calendarId "
			+ "and csw.startDate <= :endDate "
			+ "and ( csw.endDate is null or csw.endDate >= :startDate )")
	public Collection<CalendarStandardWorkday> findAllByCalendarIdBetweenDates(Long calendarId, LocalDate startDate, LocalDate endDate);

}
