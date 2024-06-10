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
import pm.employee.api.service.calendar.IWorkshiftService;
import pm.employee.common.dto.calendar.WorkshiftDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/workshifts")
public class WorkshiftController {
	
	private final IWorkshiftService service;

	/**
	 * Returns all existing workshifts
	 * 
	 * @return all workshifts
	 */
	@GetMapping
	@Operation(summary = "Returns all existing workshifts",
			description = "Retrieves every workshift available",
			responses = {@ApiResponse(responseCode = "200", description = "Workshifts retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkshiftDto>>> getWorkshifts() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(WorkshiftController.class).getWorkshifts() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing workshifts
	 * 
	 * @return all workshifts
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one workshift",
			description = "Returns the specific workshift that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Workshift retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Workshift not found")})
	public ResponseEntity<EntityModel<WorkshiftDto>> getWorkshift(
			@Parameter(description = "The identifier of the workshift") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing workshifts
	 * 
	 * @return all workshifts
	 */
	@PostMapping
	@Operation(summary = "Create a workshift",
			description = "Create a workshift with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "Workshift created successfully")})
	public ResponseEntity<EntityModel<WorkshiftDto>> createWorkshifts(
			@RequestBody @Valid WorkshiftDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing workshifts
	 * 
	 * @return all workshifts
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint workshift",
			description = "Update a workshift with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "Workshift update successfully")})
	public ResponseEntity<EntityModel<WorkshiftDto>> updateWorkshifts(
			@RequestBody @Valid WorkshiftDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
