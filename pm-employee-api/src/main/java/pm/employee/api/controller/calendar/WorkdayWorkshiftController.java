package pm.employee.api.controller.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;
import pm.employee.api.service.calendar.IWorkdayWorkshiftService;
import pm.employee.common.dto.calendar.WorkdayWorkshiftDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/workdays/{workday-id}/workshifts")
public class WorkdayWorkshiftController {
	
	private final IWorkdayWorkshiftService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping
	@Operation(summary = "Returns all existing workday workshifts of a employee",
			description = "Retrieves every workday workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<Collection<EntityModel<WorkdayWorkshiftDto>>> getWorkdayWorkshifts(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "workday-id") Long workdayId) {
		
		
		return ResponseEntity.ok(service.findAllByWorkdayId(workdayId));

	}
	
	/**
	 * Returns all existing workdays
	 * 
	 * @return all workdays
	 */
	@GetMapping("/workshifts")
	@Operation(summary = "Returns all existing workdays",
			description = "Retrieves every workday available",
			responses = {@ApiResponse(responseCode = "200", description = "Workdays retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkshiftDto>>> getWorkdaysOfWorkshift(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "workday-id") Long workdayId) {

		
		var response = CollectionModel.of(service.findWorkshiftOfWorkday(workdayId));
		
		response.add(
				linkTo( methodOn(WorkdayWorkshiftController.class).getWorkdaysOfWorkshift(workdayId) )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "workday workshift created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkdayWorkshiftDto>>> createWorkdayWorkshifts(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "workday-id") Long workdayId,
			@Valid @RequestBody List<WorkdayWorkshiftDto> employeeContractDto) {
		
		Set<Long> workdayIds = employeeContractDto
				.stream()
				.map(WorkdayWorkshiftDto::getWorkdayId).collect(Collectors.toSet());
		
		Assert.isTrue(workdayIds.size() == 1 && workdayIds.contains(workdayId), "error adding workday workshift to user. ERROR MESSAGE TODO");
		
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
			responses = {@ApiResponse(responseCode = "204", description = "workday workshift removed successfully")})
	public ResponseEntity<Void> deleteWorkdayWorkshifts(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "workday-id") Long workdayId,
			@Valid @RequestBody List<WorkdayWorkshiftDto> employeeContractDto) {
		
		Set<Long> workdayIds = employeeContractDto
				.stream()
				.map(WorkdayWorkshiftDto::getWorkdayId).collect(Collectors.toSet());
		
		Assert.isTrue(workdayIds.size() == 1 && workdayIds.contains(workdayId), "error adding workday workshift to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(e -> new WorkdayWorkshiftId(e.getWorkdayId(), e.getWorkshiftId()))
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
}
