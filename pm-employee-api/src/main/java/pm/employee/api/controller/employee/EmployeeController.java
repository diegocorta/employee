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
import pm.employee.api.service.employee.IEmployeeService;
import pm.employee.common.dto.employee.EmployeeDto;
import pm.employee.common.dto.employee.min.EmployeeMinDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/employees")
public class EmployeeController {
	
	private final IEmployeeService service;

	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@GetMapping
	@Operation(summary = "Returns all existing employees",
			description = "Retrieves every employee available",
			responses = {@ApiResponse(responseCode = "200", description = "Employees retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<EmployeeDto>>> getEmployees() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(EmployeeController.class).getEmployees() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@GetMapping("/minified")
	@Operation(summary = "Returns all existing employees minified",
			description = "Retrieves every employee available with minimum information",
			responses = {@ApiResponse(responseCode = "200", description = "Employees retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<EmployeeMinDto>>> getEmployeesMinified() {
		
		var response = CollectionModel.of(service.findAllMinified());
		
		response.add(
				linkTo( methodOn(EmployeeController.class).getEmployeesMinified() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one employee",
			description = "Returns the specific employee that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Employee not found")})
	public ResponseEntity<EntityModel<EmployeeDto>> getEmployee(
			@Parameter(description = "The identifier of the employee") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}
	
	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@GetMapping("/by-security-user/{id}")
	@Operation(summary = "Returns one employee",
			description = "Returns the specific employee that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Employee not found")})
	public ResponseEntity<EntityModel<EmployeeDto>> getEmployeeOfUser(
			@Parameter(description = "The identifier of the user") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findEmployeeBySecurityUser(id));
	}
	
	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@GetMapping("/{id}/minified")
	@Operation(summary = "Returns one employee minified",
			description = "Returns the specific employee, with minimum information, that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Employee not found")})
	public ResponseEntity<EntityModel<EmployeeMinDto>> getEmployeeMinified(
			@Parameter(description = "The identifier of the employee") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findByIdMinified(id));
	}

	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@PostMapping
	@Operation(summary = "Create a employee",
			description = "Create a employee with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "Employee created successfully")})
	public ResponseEntity<EntityModel<EmployeeDto>> createEmployees(
			@RequestBody @Valid EmployeeDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing employees
	 * 
	 * @return all employees
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint employee",
			description = "Update a employee with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "Employee update successfully")})
	public ResponseEntity<EntityModel<EmployeeDto>> updateEmployees(
			@RequestBody @Valid EmployeeDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
