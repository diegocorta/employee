package pm.employee.common.dto.recording;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
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
@Relation(collectionRelation = "employee_journal_records")
public class EmployeeJournalRecordDto 
	extends AbstractCommonDto {

	
	private static final long serialVersionUID = 6295145797365905547L;
	

	private Long id;
	
	
	@NotNull
	@PositiveOrZero
    private Long employeeId;
	
	
	@NotNull
	@PositiveOrZero
    private Long employeeJournalId;
	
	
	@NotNull
	@PositiveOrZero
    private Long recordTypeId;
	
	
	@NotBlank
    @DateTimeFormat(iso = ISO.DATE_TIME)
	private String instant;
	
	
	@NotNull
    private boolean entry;
}
