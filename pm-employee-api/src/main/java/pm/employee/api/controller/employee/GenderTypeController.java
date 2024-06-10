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
import pm.employee.api.service.employee.IGenderTypeService;
import pm.employee.common.dto.employee.GenderTypeDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/genders")
public class GenderTypeController {
	
	private final IGenderTypeService service;

	/**
	 * Returns all existing genders
	 * 
	 * @return all genders
	 */
	@GetMapping
	@Operation(summary = "Returns all existing genders",
			description = "Retrieves every gender available",
			responses = {@ApiResponse(responseCode = "200", description = "GenderTypes retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<GenderTypeDto>>> getGenderTypes() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(GenderTypeController.class).getGenderTypes() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing genders
	 * 
	 * @return all genders
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one gender",
			description = "Returns the specific gender that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "GenderType retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "GenderType not found")})
	public ResponseEntity<EntityModel<GenderTypeDto>> getGenderType(
			@Parameter(description = "The identifier of the gender") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing genders
	 * 
	 * @return all genders
	 */
	@PostMapping
	@Operation(summary = "Create a gender",
			description = "Create a gender with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "GenderType created successfully")})
	public ResponseEntity<EntityModel<GenderTypeDto>> createGenderTypes(
			@RequestBody @Valid GenderTypeDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing genders
	 * 
	 * @return all genders
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint gender",
			description = "Update a gender with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "GenderType update successfully")})
	public ResponseEntity<EntityModel<GenderTypeDto>> updateGenderTypes(
			@RequestBody @Valid GenderTypeDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
