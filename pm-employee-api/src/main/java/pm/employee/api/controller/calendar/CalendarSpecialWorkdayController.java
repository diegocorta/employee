package pm.employee.api.controller.calendar;

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
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;
import pm.employee.api.service.calendar.ICalendarSpecialWorkdayService;
import pm.employee.common.dto.calendar.CalendarSpecialWorkdayDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/calendars/{calendar-id}/special-workdays")
public class CalendarSpecialWorkdayController {
	
	private final ICalendarSpecialWorkdayService service;
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@GetMapping()
	@Operation(summary = "Returns all existing calendar workshifts of a employee",
			description = "Retrieves every calendar workshift for the specified employee identifier in the path",
			responses = {@ApiResponse(responseCode = "200", description = "contract retrieved successfully")})
	public ResponseEntity<Collection<EntityModel<CalendarSpecialWorkdayDto>>> getCalendarSpecialWorkdays(
			@Parameter(description = "The identifier of the employee") @PathVariable(name = "calendar-id") Long calendarId) {
		
		
		return ResponseEntity.ok(service.findAllByCalendarId(calendarId));

	}
	
	/**
	 * Returns all existing users
	 * 
	 * @return all users
	 */
	@PostMapping()
	@Operation(summary = "Create a contract",
			description = "Create a contract with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "calendar workshift created successfully")})
	public ResponseEntity<CollectionModel<EntityModel<CalendarSpecialWorkdayDto>>> createCalendarSpecialWorkdays(
			@Parameter(description = "The identifier of the user") @PathVariable(name = "calendar-id") Long calendarId,
			@Valid @RequestBody List<CalendarSpecialWorkdayDto> employeeContractDto) {
		
		Set<Long> calendarIds = employeeContractDto
				.stream()
				.map(CalendarSpecialWorkdayDto::getCalendarId).collect(Collectors.toSet());
		
		Assert.isTrue(calendarIds.size() == 1 && calendarIds.contains(calendarId), "error adding calendar workshift to user. ERROR MESSAGE TODO");
		
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
	public ResponseEntity<Void> deleteCalendarSpecialWorkdays(
			@Parameter(description = "the identifier of the user") @PathVariable(name = "calendar-id") Long calendarId,
			@Valid @RequestBody List<CalendarSpecialWorkdayDto> employeeContractDto) {
		
		Set<Long> calendarIds = employeeContractDto
				.stream()
				.map(CalendarSpecialWorkdayDto::getCalendarId).collect(Collectors.toSet());
		
		Assert.isTrue(calendarIds.size() == 1 && calendarIds.contains(calendarId), "error adding calendar workshift to user. ERROR MESSAGE TODO");
		
		service.deleteByIds(employeeContractDto
				.stream()
				.map(e -> new CalendarWorkdayId(e.getCalendarId(), e.getWorkdayId()))
				.collect(Collectors.toList()));
				
		return ResponseEntity.noContent().build();
	}
	
}
