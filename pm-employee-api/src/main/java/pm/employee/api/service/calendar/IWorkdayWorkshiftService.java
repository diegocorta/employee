package pm.employee.api.service.calendar;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;
import pm.employee.common.dto.calendar.WorkdayWorkshiftDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

public interface IWorkdayWorkshiftService extends ICommonService<WorkdayWorkshiftDto, WorkdayWorkshiftId> {

	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkdayId(Long workdayId);
		
	public Collection<EntityModel<WorkshiftDto>> findWorkshiftOfWorkday(Long workdayId);
	
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkshiftId(Long worksihftId);
		
	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkdayIdIn(Collection<Long> workdayIds);

	public Collection<EntityModel<WorkdayWorkshiftDto>> findAllByWorkshiftIdIn(Collection<Long> workshiftIds);
	
}