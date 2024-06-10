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
import pm.employee.api.assembler.employee.JobTypeAssembler;
import pm.employee.api.entity.employee.JobType;
import pm.employee.api.repository.employee.IJobTypeRepository;
import pm.employee.api.service.employee.IJobTypeService;
import pm.employee.common.dto.employee.JobTypeDto;

@Service
public class JobTypeServiceImpl extends BasicService<IJobTypeRepository, JobType, Long, JobTypeDto, JobTypeAssembler>
		implements IJobTypeService {
		
	public JobTypeServiceImpl(IJobTypeRepository repository,
			IJobTypeRepository jobTypeRepository) {
		
		super(JobType.class, repository, JobTypeAssembler.getInstance());
	
	}

	@Override
	public Map<JobTypeDto, JoinEntityMap> getRelatedEntities(Collection<JobTypeDto> dtos) {
		
		return null;
	}

	@Override
	public void basicDataValidation(Collection<JobTypeDto> dtos) {
		
		for (JobTypeDto dto : dtos) {
			
			JobType job = new JobType();
			job.setName(dto.getName());
			
			Optional<JobType> prevJob = repository.findOne(Example.of(job));
			boolean nameDuplicated = false;
			
			if (nameDuplicated) {
				
				Assert.isNull(dto.getId(), MessageUtils.identifierMustBeNull(JobType.DEFAULT_DESCRIPTION));
				if (prevJob.isPresent()) nameDuplicated = true;
				
			} else {
				
				Assert.notNull(dto.getId(), MessageUtils.identifierMustNotBeNull(JobType.DEFAULT_DESCRIPTION));
				if (prevJob.isPresent() && !prevJob.get().getId().equals(dto.getId()))
					nameDuplicated = true;
				
			}
			
			if (nameDuplicated) {
				throw new EntityExistsException(MessageUtils.entityAlrreadyExistsExceptionMessage(JobType.DEFAULT_DESCRIPTION));
			}
		}
		
	}

	@Override
	public void createDataValidation(Collection<JobTypeDto> dtos) {
		// TODO Auto-generated method stub
		
	}
	
}
 