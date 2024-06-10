package pm.employee.common.dto.employee;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
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
@Relation(collectionRelation = "contracts")
public class EmployeeCalendarWorkshiftDto 
	extends AbstractCommonDto {
	
	private static final long serialVersionUID = 1215314791830718123L;


	@NotNull
	@PositiveOrZero
    private Long employeeId;
	
	
	@NotNull
	@PositiveOrZero
    private Long calendarId;
    
    
	@NotNull
	@PositiveOrZero
    private Long workshiftId;
    
    
	@NotBlank
    @DateTimeFormat(iso = ISO.DATE)
	private String startDate;
    
    
	@Nullable
    @DateTimeFormat(iso = ISO.DATE)
	private String endDate;
    
	
}
