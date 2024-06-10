package pm.employee.api.controller.employee;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import pm.employee.api.service.employee.IEmployeeContractService;
import pm.employee.common.dto.employee.EmployeeContractDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees/{employee-id}/contracts")
public class EmployeeContractController {
	
	private final IEmployeeContractService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping
	@Operation(summary = "Returns all existing contracts of a employee",
			description = "Retrieves every contracts for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<Collection<EntityModel<EmployeeContractDto>>> getEmployeeContracts(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId) {
		
		
		return ResponseEntity.ok(service.findAllByEmployeeId(employeeId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/last")
	@Operation(summary = "Returns last existing contracts of a employee",
			description = "Retrieves last contract for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeContractDto>> getLastEmployeeContracts(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId) {
		
		
		return ResponseEntity.ok(service.findLastByEmployeeId(employeeId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/{employee-contract-id}")
	@Operation(summary = "Returns all existing contracts of a employee",
			description = "Retrieves every contracts for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeContractDto>> getEmployeeContract(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@Parameter(description = "The identifier of the contract") @PathVariable(name = "employee-contract-id") Long employeeContractIdId) {
		
		
		return ResponseEntity.ok(service.findById(employeeId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PutMapping("/{employee-contract-id}")
	@Operation(summary = "Updated the existing contracts of a employee",
			description = "Retrieves every contracts for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeContractDto>> updateEmployeeContract(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@Parameter(description = "The identifier of the contract") @PathVariable(name = "employee-contract-id") Long employeeContractIdId,
			@Valid @RequestBody EmployeeContractDto employeeContractDto) {

		Assert.isTrue(employeeContractDto.getId().equals(employeeContractIdId), "todo. error update employee contract");
		Assert.isTrue(employeeContractDto.getEmployeeId().equals(employeeId), "todo. error update employee contract");

		return ResponseEntity.ok(service.update(employeeContractDto));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "contracts created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<EmployeeContractDto>>> createEmployeeContracts(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<pm.employee.common.dto.employee.EmployeeContractDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeContractDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error adding contracts to user. ERROR MESSAGE TODO");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(CollectionModel.of(
				service.saveAll(employeeContractDto)));
	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@DeleteMapping
	@Operation(summary = "Removes the existing contract",
			description = "Removes all the employee that matches the information provided",
			responses = {@ApiResponse(responseCode = "204", description = "contracts removed successfully")})
	public ResponseEntity<Void> deleteEmployeeContracts(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<pm.employee.common.dto.employee.EmployeeContractDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeContractDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error deleting contracts to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(EmployeeContractDto::getId)
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
	
}
