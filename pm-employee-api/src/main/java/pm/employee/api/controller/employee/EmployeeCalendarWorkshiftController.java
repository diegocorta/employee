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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;
import pm.employee.api.service.employee.IEmployeeCalendarWorkshiftService;
import pm.employee.common.dto.employee.EmployeeCalendarWorkshiftDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees/{employee-id}")
public class EmployeeCalendarWorkshiftController {
	
	private final IEmployeeCalendarWorkshiftService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/calendar-workshifts")
	@Operation(summary = "Returns all existing calendar workshifts of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<Collection<EntityModel<EmployeeCalendarWorkshiftDto>>> getEmployeeCalendarWorkshifts(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId) {
		
		
		return ResponseEntity.ok(service.findAllByEmployeeId(employeeId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/calendars/{calendar-id}/workshifts/{workshift-id}")
	@Operation(summary = "Returns all existing calendar workshift of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<EmployeeCalendarWorkshiftDto>> getEmployeeCalendarWorkshift(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "employee-id") Long employeeId,
			@Parameter(description = "The identifier of the calendar") @PathVariable(name = "calendar-id") Long calendarId,
			@Parameter(description = "The identifier of the workshift") @PathVariable(name = "workshifts-id") Long workshift) {
		
		
		return ResponseEntity.ok(service.findById(new EmployeeCalendarWorkshiftId(employeeId, calendarId, workshift)));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping("/calendar-workshifts")
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "calendar workshift created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<EmployeeCalendarWorkshiftDto>>> createEmployeeCalendarWorkshifts(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<EmployeeCalendarWorkshiftDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeCalendarWorkshiftDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error adding calendar workshift to user. ERROR MESSAGE TODO");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(CollectionModel.of(
				service.saveAll(employeeContractDto)));
	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@DeleteMapping("/calendar-workshifts")
	@Operation(summary = "Removes the existing contract",
			description = "Removes all the employee that matches the information provided",
			responses = {@ApiResponse(responseCode = "204", description = "calendar workshift removed successfully")})
	public ResponseEntity<Void> deleteEmployeeCalendarWorkshifts(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "employee-id") Long employeeId,
			@Valid @RequestBody List<EmployeeCalendarWorkshiftDto> employeeContractDto) {
		
		Set<Long> employeeIds = employeeContractDto
				.stream()
				.map(EmployeeCalendarWorkshiftDto::getEmployeeId).collect(Collectors.toSet());
		
		Assert.isTrue(employeeIds.size() == 1 && employeeIds.contains(employeeId), "error deleting calendar workshift to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(e -> new EmployeeCalendarWorkshiftId(e.getEmployeeId(), e.getCalendarId(), e.getWorkshiftId()))
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
}
