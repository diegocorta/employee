package pm.employee.api.service.recording;

import java.util.Collection;

import org.springframework.hateoas.EntityModel;

import es.common.service.ICommonService;
import pm.employee.common.dto.recording.RecordTypeDto;

public interface IRecordTypeService extends ICommonService<RecordTypeDto, Long> {

	EntityModel<RecordTypeDto> findFirstByName(String name);
	
	Collection<EntityModel<RecordTypeDto>> findAllByNameIn(Collection<String> names);
	
}