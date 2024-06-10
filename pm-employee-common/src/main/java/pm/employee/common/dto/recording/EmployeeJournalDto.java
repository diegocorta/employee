package pm.employee.common.dto.recording;

import java.util.TimeZone;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
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
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "employee_journals")
public class EmployeeJournalDto 
	extends AbstractCommonDto {

	
	private static final long serialVersionUID = -6758840287170640500L;

	public static final String RECORDS_REL = "records";

	private Long id;
	
	@NotNull
	@PositiveOrZero
	private Long employeeId;
	
	
	@NotBlank
	@DateTimeFormat(iso = ISO.DATE)
	private String date;
	
	
    @NotNull
    @Valid
    private TimeZone timezone;
    
    
    @NotBlank
    @DateTimeFormat(iso = ISO.DATE_TIME)
	private String journalStart;
	
	
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
	private String journalEnd;
}
