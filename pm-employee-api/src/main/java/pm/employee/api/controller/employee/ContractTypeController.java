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
import pm.employee.api.service.employee.IContractTypeService;
import pm.employee.common.dto.employee.ContractTypeDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/contracts")
public class ContractTypeController {
	
	private final IContractTypeService service;

	/**
	 * Returns all existing contracts
	 * 
	 * @return all contracts
	 */
	@GetMapping
	@Operation(summary = "Returns all existing contracts",
			description = "Retrieves every contract available",
			responses = {@ApiResponse(responseCode = "200", description = "ContractTypes retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<ContractTypeDto>>> getContractTypes() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(ContractTypeController.class).getContractTypes() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing contracts
	 * 
	 * @return all contracts
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one contract",
			description = "Returns the specific contract that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "ContractType retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "ContractType not found")})
	public ResponseEntity<EntityModel<ContractTypeDto>> getContractType(
			@Parameter(description = "The identifier of the contract") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing contracts
	 * 
	 * @return all contracts
	 */
	@PostMapping
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "ContractType created successfully")})
	public ResponseEntity<EntityModel<ContractTypeDto>> createContractTypes(
			@RequestBody @Valid ContractTypeDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing contracts
	 * 
	 * @return all contracts
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint contract",
			description = "Update a contract with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "ContractType update successfully")})
	public ResponseEntity<EntityModel<ContractTypeDto>> updateContractTypes(
			@RequestBody @Valid ContractTypeDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
