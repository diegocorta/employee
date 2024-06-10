package pm.employee.api.service.calendar.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.common.service.BasicService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import pm.employee.api.assembler.calendar.WorkshiftAssembler;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.repository.calendar.IWorkshiftRepository;
import pm.employee.api.service.calendar.IWorkshiftService;
import pm.employee.common.dto.calendar.WorkshiftDto;

@Service
public class WorkshiftServiceImpl extends BasicService<IWorkshiftRepository, Workshift, Long, WorkshiftDto, WorkshiftAssembler>
		implements IWorkshiftService {
		
	public WorkshiftServiceImpl(IWorkshiftRepository repository,
			IWorkshiftRepository genderTypeRepository) {
		
		super(Workshift.class, repository, WorkshiftAssembler.getInstance());
	
	}

	@Override
	public Map<WorkshiftDto, JoinEntityMap> getRelatedEntities(Collection<WorkshiftDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<WorkshiftDto> dtos) {
		
		for (WorkshiftDto dto : dtos) {
			
			Workshift contract = new Workshift();
			contract.setName(dto.getName());
			
			Optional<Workshift> prevContract = repository.findOne(Example.of(contract));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(Workshift.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(Workshift.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent() && !prevContract.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(Workshift.DEFAULT_DESCRIPTION));
			}
		}
	}

	@Override
	public void createDataValidation(Collection<WorkshiftDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityModel<WorkshiftDto> findFirstByName(String name) {
		
		return assembler.buildDtoWithLinksFromEntity(
				repository.findFirstByName(name)
					.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(Workshift.DEFAULT_DESCRIPTION))));
	}

	@Override
	public Collection<EntityModel<WorkshiftDto>> findAllByNameIn(Collection<String> names) {
		
		return repository.findAllByNameIn(names)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}
 