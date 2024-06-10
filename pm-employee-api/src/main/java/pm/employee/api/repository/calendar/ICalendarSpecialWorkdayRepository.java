package pm.employee.api.repository.calendar;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pm.employee.api.entity.calendar.CalendarSpecialWorkday;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;

public interface ICalendarSpecialWorkdayRepository 
		extends JpaRepository<CalendarSpecialWorkday, CalendarWorkdayId> {
	
	public Collection<CalendarSpecialWorkday> findAllByWorkdayId(Long workdayId);
	
	public Collection<CalendarSpecialWorkday> findAllByCalendarId(Long calendarId);
	
	public Collection<CalendarSpecialWorkday> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<CalendarSpecialWorkday> findAllByCalendarIdIn(Collection<Long> calendarIds);

	@Query("select csw from CalendarSpecialWorkday csw "
			+ "inner join csw.calendar c "
			+ "where c.id = :calendarId "
			+ "and csw.startDate <= :endDate "
			+ "and (csw.endDate is null or csw.endDate >= :startDate )")
	public Collection<CalendarSpecialWorkday> findAllByCalendarIdBetweenDates(Long calendarId, LocalDate startDate, LocalDate endDate);

}
