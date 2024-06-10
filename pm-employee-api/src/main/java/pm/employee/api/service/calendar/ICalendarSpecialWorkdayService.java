package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;
import pm.employee.common.dto.calendar.CalendarSpecialWorkdayDto;

public interface ICalendarSpecialWorkdayService extends ICommonService<CalendarSpecialWorkdayDto, CalendarWorkdayId> {

	public Collection<EntityModel<CalendarSpecialWorkdayDto>> findAllByWorkdayId(Long workdayId);
	
	public Collection<EntityModel<CalendarSpecialWorkdayDto>> findAllByCalendarId(Long calendarId);
	
	public Collection<EntityModel<CalendarSpecialWorkdayDto>> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<EntityModel<CalendarSpecialWorkdayDto>> findAllByCalendarIdIn(Collection<Long> calendarIds);
	
}