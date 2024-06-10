package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.calendar.WorkshiftDto;

public interface IWorkshiftService extends ICommonService<WorkshiftDto, Long> {

	EntityModel<WorkshiftDto> findFirstByName(String name);
	
	Collection<EntityModel<WorkshiftDto>> findAllByNameIn(Collection<String> names);
	
}