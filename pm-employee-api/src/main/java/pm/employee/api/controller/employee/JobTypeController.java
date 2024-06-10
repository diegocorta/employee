package pm.employee.api.controller.employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pm.employee.api.service.employee.IJobTypeService;
import pm.employee.common.dto.employee.JobTypeDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/jobs")
public class JobTypeController {
	
	private final IJobTypeService service;

	/**
	 * Returns all existing jobs
	 * 
	 * @return all jobs
	 */
	@GetMapping
	@Operation(summary = "Returns all existing jobs",
			description = "Retrieves every job available",
			responses = {@ApiResponse(responseCode = "200", description = "JobTypes retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<JobTypeDto>>> getJobTypes() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(JobTypeController.class).getJobTypes() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing jobs
	 * 
	 * @return all jobs
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one job",
			description = "Returns the specific job that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "JobType retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "JobType not found")})
	public ResponseEntity<EntityModel<JobTypeDto>> getJobType(
			@Parameter(description = "The identifier of the job") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing jobs
	 * 
	 * @return all jobs
	 */
	@PostMapping
	@Operation(summary = "Create a job",
			description = "Create a job with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "JobType created successfully")})
	public ResponseEntity<EntityModel<JobTypeDto>> createJobTypes(
			@RequestBody @Valid JobTypeDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing jobs
	 * 
	 * @return all jobs
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint job",
			description = "Update a job with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "JobType update successfully")})
	public ResponseEntity<EntityModel<JobTypeDto>> updateJobTypes(
			@RequestBody @Valid JobTypeDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
