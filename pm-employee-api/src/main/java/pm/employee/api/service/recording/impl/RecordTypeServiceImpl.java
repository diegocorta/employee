package pm.employee.api.service.recording.impl;

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
import pm.employee.api.assembler.recording.RecordTypeAssembler;
import pm.employee.api.entity.recording.RecordType;
import pm.employee.api.repository.recording.IRecordTypeRepository;
import pm.employee.api.service.recording.IRecordTypeService;
import pm.employee.common.dto.recording.RecordTypeDto;

@Service
public class RecordTypeServiceImpl extends BasicService<IRecordTypeRepository, RecordType, Long, RecordTypeDto, RecordTypeAssembler>
		implements IRecordTypeService {
		
	public RecordTypeServiceImpl(IRecordTypeRepository repository,
			IRecordTypeRepository genderTypeRepository) {
		
		super(RecordType.class, repository, RecordTypeAssembler.getInstance());
	
	}

	@Override
	public Map<RecordTypeDto, JoinEntityMap> getRelatedEntities(Collection<RecordTypeDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<RecordTypeDto> dtos) {
		
		for (RecordTypeDto dto : dtos) {
			
			RecordType contract = new RecordType();
			contract.setName(dto.getName());
			
			Optional<RecordType> prevContract = repository.findOne(Example.of(contract));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(RecordType.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(RecordType.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent() && !prevContract.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(RecordType.DEFAULT_DESCRIPTION));
			}
		}
		
	}

	@Override
	public void createDataValidation(Collection<RecordTypeDto> dtos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityModel<RecordTypeDto> findFirstByName(String name) {
		
		return assembler.buildDtoWithLinksFromEntity(
				repository.findFirstByName(name)
					.orElseThrow(() -> new EntityNotFoundException(MessageUtils.entityNotFoundExceptionMessage(RecordType.DEFAULT_DESCRIPTION))));
	}

	@Override
	public Collection<EntityModel<RecordTypeDto>> findAllByNameIn(Collection<String> names) {
		
		return repository.findAllByNameIn(names)
				.stream()
				.map(ec -> assembler.buildDtoWithLinksFromEntity(ec))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}
 