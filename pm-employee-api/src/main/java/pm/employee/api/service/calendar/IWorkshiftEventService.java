package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.calendar.WorkshiftEventDto;

public interface IWorkshiftEventService extends ICommonService<WorkshiftEventDto, Long> {

	Collection<EntityModel<WorkshiftEventDto>> findAllByWorkshiftId(Long workshiftId);
	
	Collection<EntityModel<WorkshiftEventDto>> findAllByWorkshiftIdIn(Collection<Long> workshiftIds);
	
}