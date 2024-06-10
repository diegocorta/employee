package pm.employee.api.controller.recording;

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
import pm.employee.api.service.recording.IRecordTypeService;
import pm.employee.common.dto.recording.RecordTypeDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/record-types")
public class RecordTypeController {
	
	private final IRecordTypeService service;

	/**
	 * Returns all existing record-type
	 * 
	 * @return all record-type
	 */
	@GetMapping
	@Operation(summary = "Returns all existing record-type",
			description = "Retrieves every record-type available",
			responses = {@ApiResponse(responseCode = "200", description = "RecordTypes retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<RecordTypeDto>>> getRecordTypes() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(RecordTypeController.class).getRecordTypes() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing record-type
	 * 
	 * @return all record-type
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one record-type",
			description = "Returns the specific record-type that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "RecordType retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "RecordType not found")})
	public ResponseEntity<EntityModel<RecordTypeDto>> getRecordType(
			@Parameter(description = "The identifier of the record-type") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing record-type
	 * 
	 * @return all record-type
	 */
	@PostMapping
	@Operation(summary = "Create a record-type",
			description = "Create a record-type with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "RecordType created successfully")})
	public ResponseEntity<EntityModel<RecordTypeDto>> createRecordTypes(
			@RequestBody @Valid RecordTypeDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing record-type
	 * 
	 * @return all record-type
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint record-type",
			description = "Update a record-type with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "RecordType update successfully")})
	public ResponseEntity<EntityModel<RecordTypeDto>> updateRecordTypes(
			@RequestBody @Valid RecordTypeDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
