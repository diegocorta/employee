package pm.employee.common.dto.recording;

import java.util.TimeZone;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeJournalRecordToRegisterDto {

	@NotNull
	@PositiveOrZero
    private Long employeeId;
	
	
	@Nullable
	@PositiveOrZero
    private Long employeeJournalId;
	
	
	@NotNull
	@PositiveOrZero
    private Long recordTypeId;
	
	
	@NotBlank
    @DateTimeFormat(iso = ISO.DATE_TIME)
	private String instant;
	
	
	@Nullable
	@DateTimeFormat(iso = ISO.DATE)
	private String date;
	
	
	@Nullable
    @Valid
    private TimeZone timezone;
	
	
	@NotNull
    private boolean entry;
	
}
