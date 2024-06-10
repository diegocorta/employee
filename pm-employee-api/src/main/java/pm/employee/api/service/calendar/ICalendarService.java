package pm.employee.api.service.calendar;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.calendar.CalendarDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

public interface ICalendarService extends ICommonService<CalendarDto, Long> {

	EntityModel<CalendarDto> findFirstByName(String name);
	
	Collection<EntityModel<CalendarDto>> findAllByNameIn(Collection<String> names);

	Map<LocalDate, Collection<EntityModel<WorkshiftDto>>> findWorkshiftsByDates(Long id, LocalDate startDate, LocalDate endDate);
	
}