package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.calendar.WorkdayDto;

public interface IWorkdayService extends ICommonService<WorkdayDto, Long> {

	EntityModel<WorkdayDto> findFirstByName(String name);
	
	Collection<EntityModel<WorkdayDto>> findAllByNameIn(Collection<String> names);
	
}