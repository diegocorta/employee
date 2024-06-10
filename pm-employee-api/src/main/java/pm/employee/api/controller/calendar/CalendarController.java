package pm.employee.api.controller.calendar;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pm.employee.api.service.calendar.ICalendarService;
import pm.employee.common.dto.calendar.CalendarDto;
import pm.employee.common.dto.calendar.WorkshiftDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/calendars")
public class CalendarController {
	
	private final ICalendarService service;

	/**
	 * Returns all existing calendar
	 * 
	 * @return all calendar
	 */
	@GetMapping
	@Operation(summary = "Returns all existing calendar",
			description = "Retrieves every calendar available",
			responses = {@ApiResponse(responseCode = "200", description = "Calendars retrieved successfully")})
	public ResponseEntity<CollectionModel<EntityModel<CalendarDto>>> getCalendars() {
		
		var response = CollectionModel.of(service.findAll());
		
		response.add(
				linkTo( methodOn(CalendarController.class).getCalendars() )
						.withSelfRel() );
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Returns all existing calendar
	 * 
	 * @return all calendar
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Returns one calendar",
			description = "Returns the specific calendar that matches the identifier",
			responses = {
					@ApiResponse(responseCode = "200", description = "Calendar retrieved successfully"),
					@ApiResponse(responseCode = "404", description = "Calendar not found")})
	public ResponseEntity<EntityModel<CalendarDto>> getCalendar(
			@Parameter(description = "The identifier of the calendar") @PathVariable Long id) {
		
		return ResponseEntity.ok(service.findById(id));
	}

	/**
	 * Returns all existing calendar
	 * 
	 * @return all calendar
	 */
	@GetMapping("/{id}/effective-workshifts")
	@Operation(summary = "Returns all existing calendar",
			description = "Retrieves every calendar available",
			responses = {@ApiResponse(responseCode = "200", description = "Calendars retrieved successfully")})
	public ResponseEntity<Map<LocalDate, Collection<EntityModel<WorkshiftDto>>>> getCalendarWorkshifts(
			@Parameter(description = "The identifier of the calendar") @PathVariable Long id,
			@RequestParam(name = "start-date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "end-date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		
		return ResponseEntity.ok(service.findWorkshiftsByDates(id, startDate, endDate));
		
	}
	
	/**
	 * Returns all existing calendar
	 * 
	 * @return all calendar
	 */
	@PostMapping
	@Operation(summary = "Create a calendar",
			description = "Create a calendar with the information provided",
			responses = {@ApiResponse(responseCode = "201", description = "Calendar created successfully")})
	public ResponseEntity<EntityModel<CalendarDto>> createCalendars(
			@RequestBody @Valid CalendarDto dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
	}
	
	/**
	 * Returns all existing calendar
	 * 
	 * @return all calendar
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update an existint calendar",
			description = "Update a calendar with the information provided",
			responses = {@ApiResponse(responseCode = "200", description = "Calendar update successfully")})
	public ResponseEntity<EntityModel<CalendarDto>> updateCalendars(
			@RequestBody @Valid CalendarDto dto) {
		
		return ResponseEntity.ok(service.update(dto));
	}
	
}
