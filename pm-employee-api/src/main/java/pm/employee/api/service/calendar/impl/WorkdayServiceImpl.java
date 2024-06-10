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
import pm.employee.api.assembler.calendar.WorkdayAssembler;
import pm.employee.api.entity.calendar.Workday;
import pm.employee.api.repository.calendar.IWorkdayRepository;
import pm.employee.api.service.calendar.IWorkdayService;
import pm.employee.common.dto.calendar.WorkdayDto;

@Service
public class WorkdayServiceImpl extends BasicService<IWorkdayRepository, Workday, Long, WorkdayDto, WorkdayAssembler>
		implements IWorkdayService {
		
	public WorkdayServiceImpl(IWorkdayRepository repository,
			IWorkdayRepository genderTypeRepository) {
		
		super(Workday.class, repository, WorkdayAssembler.getInstance());
	
	}

	@Override
	public Map<WorkdayDto, JoinEntityMap> getRelatedEntities(Collection<WorkdayDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<WorkdayDto> dtos) {
		
		for (WorkdayDto dto : dtos) {
			
			Workday contract = new Workday();
			contract.setName(dto.getName());
			
			Optional<Workday> prevContract = repository.findOne(Example.of(contract));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(Workday.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(Workday.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent() && !prevContract.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(Workday.DEFAULT_DESCRIPTION));
			}
		}
	}

	@Override
	public void createDataValidation(Collection<WorkdayDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityModel<WorkdayDto> findFirstByName(String name) {
		
		return assembler.buildDtoWithLinksFromEntity(
				repository.findFirstByName(name)
					.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(Workday.DEFAULT_DESCRIPTION))));
	}

	@Override
	public Collection<EntityModel<WorkdayDto>> findAllByNameIn(Collection<String> names) {
		
		return repository.findAllByNameIn(names)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}
 