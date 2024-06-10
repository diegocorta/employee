package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;
import pm.employee.common.dto.calendar.CalendarStandardWorkdayDto;

public interface ICalendarStandardWorkdayService extends ICommonService<CalendarStandardWorkdayDto, CalendarWorkdayId> {

	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByWorkdayId(Long workdayId);
	
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByCalendarId(Long calendarId);
	
	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<EntityModel<CalendarStandardWorkdayDto>> findAllByCalendarIdIn(Collection<Long> calendarIds);
	
}