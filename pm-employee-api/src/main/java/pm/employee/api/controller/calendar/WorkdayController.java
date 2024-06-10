package pm.employee.api.controller.calendar;

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
import pm.employee.api.service.calendar.IWorkdayService;
import pm.employee.common.dto.calendar.WorkdayDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/workdays")
public class WorkdayController {
	
	private final IWorkdayService service;

	/**
	 * Returns all existing workdays
	 * 
	 * @return all workdays
	 */
	@GetMapping
	@Operation(summary = "Returns all existing workdays",
			description = "Retrieves every workday available",
			responses = {@ApiResponse(responseCode = "200", description = "Workdays retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkdayDto>>> getWorkdays() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(WorkdayController.class).getWorkdays() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing workdays
	 * 
	 * @return all workdays
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one workday",
			description = "Returns the specific workday that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Workday retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Workday not found")})
	public ResponseEntity<EntityModel<WorkdayDto>> getWorkday(
			@Parameter(description = "The identifier of the workday") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing workdays
	 * 
	 * @return all workdays
	 */
	@PostMapping
	@Operation(summary = "Create a workday",
			description = "Create a workday with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "Workday created successfully")})
	public ResponseEntity<EntityModel<WorkdayDto>> createWorkdays(
			@RequestBody @Valid WorkdayDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing workdays
	 * 
	 * @return all workdays
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint workday",
			description = "Update a workday with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "Workday update successfully")})
	public ResponseEntity<EntityModel<WorkdayDto>> updateWorkdays(
			@RequestBody @Valid WorkdayDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
