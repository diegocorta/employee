package pm.employee.api.service.employee.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.common.service.BasicService;
import es.common.util.JoinEntityMap;
import es.common.util.MessageUtils;
import jakarta.persistence.EntityExistsException;
import pm.employee.api.assembler.employee.GenderTypeAssembler;
import pm.employee.api.entity.employee.GenderType;
import pm.employee.api.repository.employee.IGenderTypeRepository;
import pm.employee.api.service.employee.IGenderTypeService;
import pm.employee.common.dto.employee.GenderTypeDto;

@Service
public class GenderTypeServiceImpl extends BasicService<IGenderTypeRepository, GenderType, Long, GenderTypeDto, GenderTypeAssembler>
		implements IGenderTypeService {
		
	public GenderTypeServiceImpl(IGenderTypeRepository repository,
			IGenderTypeRepository genderTypeRepository) {
		
		super(GenderType.class, repository, GenderTypeAssembler.getInstance());
	
	}

	@Override
	public Map<GenderTypeDto, JoinEntityMap> getRelatedEntities(Collection<GenderTypeDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<GenderTypeDto> dtos) {
		
		for (GenderTypeDto dto : dtos) {
			
			GenderType gender = new GenderType();
			gender.setName(dto.getName());
			
			Optional<GenderType> prevGender = repository.findOne(Example.of(gender));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(GenderType.DEFAULT_DESCRIPTION));
				if (prevGender.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(GenderType.DEFAULT_DESCRIPTION));
				if (prevGender.isPresent() && !prevGender.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(GenderType.DEFAULT_DESCRIPTION));
			}
		}
		
	}

	@Override
	public void createDataValidation(Collection<GenderTypeDto> dtos) {
		// TODO Auto-generated method stub
		
	}
	
}
 