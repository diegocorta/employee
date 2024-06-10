package pm.employee.api.controller.recording;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pm.employee.api.service.recording.IEmployeeJournalService;
import pm.employee.common.dto.recording.EmployeeJournalDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees/{employee-id}/journals")
public class EmployeeJournalController {
	
	private final IEmployeeJournalService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping
	@Operation(summary = "Returns all existing calendar workshifts of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<Collection<EntityModel<EmployeeJournalDto>>> getEmployeeJournals(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@PathVariable(name = "start-date", required = true) LocalDate startDate,
			@PathVariable(name = "end-date", required = true) LocalDate endDate) {
		
		
		return ResponseEntity.ok(service.findAllByEmployeeIdAndDateBetween(employeeId, startDate, endDate));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/of-date/{date}")
	@Operation(summary = "Returns all existing calendar workshifts of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeJournalDto>> getEmployeeJournal(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "date") LocalDate date) {
		
		return ResponseEntity.ok(service.findByEmployeeIdAndDate(employeeId, date));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("{id}")
	@Operation(summary = "Returns all existing calendar workshifts of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeJournalDto>> getEmployeeJournal(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "id") Long employeeJournalId) {
		
		return ResponseEntity.ok(service.findById(employeeJournalId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "calendar workshift created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<EmployeeJournalDto>>> createEmployeeJournals(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<EmployeeJournalDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeJournalDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error adding calendar workshift to user. ERROR MESSAGE TODO");
		
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
			responses = {@ApiResponse(responseCode = "204", description = "calendar workshift removed successfully")})
	public ResponseEntity<Void> deleteEmployeeJournals(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<EmployeeJournalDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeJournalDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error deleting calendar workshift to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(EmployeeJournalDto::getId)
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
}
