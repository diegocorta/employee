package pm.employee.api.controller.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import pm.employee.api.service.calendar.IWorkshiftEventService;
import pm.employee.common.dto.calendar.WorkshiftEventDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/workshifts/{workshift-id}/events")
public class WorkshiftEventController {
	
	private final IWorkshiftEventService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping
	@Operation(summary = "Returns all existing workday workshifts of a employee",
			description = "Retrieves every workday workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkshiftEventDto>>> getWorkshiftEvents(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "workshift-id") Long workshiftId) {
		
		var response = CollectionModel.of(service.findAllByWorkshiftId(workshiftId));
		
		response.add(
				linkTo( methodOn(WorkshiftEventController.class).getWorkshiftEvents(workshiftId) )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns all existing workday workshifts of a employee",
			description = "Retrieves every workday workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<EntityModel<WorkshiftEventDto>> getOneWorkshiftEvents(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "workshift-id") Long workshiftId,
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "id") Long id) {
		
		return ResponseEntity.ok(service.findById(id));
		
	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping()
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "workday workshift created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<WorkshiftEventDto>>> createWorkshiftEvents(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "workday-id") Long workdayId,
			@Valid @RequestBody List<WorkshiftEventDto> employeeContractDto) {
		
		Set<Long> workshiftIds = employeeContractDto
				.stream()
				.map(WorkshiftEventDto::getWorkshiftId).collect(Collectors.toSet());
		
		Assert.isTrue(workshiftIds.size() == 1 && workshiftIds.contains(workdayId), "error adding workday workshift to user. ERROR MESSAGE TODO");
		
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
	public ResponseEntity<Void> deleteWorkshiftEvents(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "workday-id") Long workdayId,
			@Valid @RequestBody List<WorkshiftEventDto> employeeContractDto) {
		
		Set<Long> workshiftIds = employeeContractDto
				.stream()
				.map(WorkshiftEventDto::getWorkshiftId).collect(Collectors.toSet());
		
		Assert.isTrue(workshiftIds.size() == 1 && workshiftIds.contains(workdayId), "error adding workday workshift to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(WorkshiftEventDto::getId)
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
}
