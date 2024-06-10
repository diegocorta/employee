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
import pm.employee.api.assembler.employee.ContractTypeAssembler;
import pm.employee.api.entity.employee.ContractType;
import pm.employee.api.repository.employee.IContractTypeRepository;
import pm.employee.api.service.employee.IContractTypeService;
import pm.employee.common.dto.employee.ContractTypeDto;

@Service
public class ContractTypeServiceImpl extends BasicService<IContractTypeRepository, ContractType, Long, ContractTypeDto, ContractTypeAssembler>
		implements IContractTypeService {
		
	public ContractTypeServiceImpl(IContractTypeRepository repository,
			IContractTypeRepository genderTypeRepository) {
		
		super(ContractType.class, repository, ContractTypeAssembler.getInstance());
	
	}

	@Override
	public Map<ContractTypeDto, JoinEntityMap> getRelatedEntities(Collection<ContractTypeDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<ContractTypeDto> dtos) {
		
		for (ContractTypeDto dto : dtos) {
			
			ContractType contract = new ContractType();
			contract.setName(dto.getName());
			
			Optional<ContractType> prevContract = repository.findOne(Example.of(contract));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(ContractType.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(ContractType.DEFAULT_DESCRIPTION));
				if (prevContract.isPresent() && !prevContract.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(ContractType.DEFAULT_DESCRIPTION));
			}
		}
		
	}

	@Override
	public void createDataValidation(Collection<ContractTypeDto> dtos) {
		// TODO Auto-generated method stub
		
	}
	
}
 