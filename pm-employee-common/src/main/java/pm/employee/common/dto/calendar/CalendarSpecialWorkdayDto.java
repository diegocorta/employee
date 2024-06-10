package pm.employee.common.dto.calendar;

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
@Relation(collectionRelation = "calendarSpecialWorkdays")
public class CalendarSpecialWorkdayDto 
	extends AbstractCommonDto {

	
	private static final long serialVersionUID = -6435493578252242413L;


	@NotNull
	@PositiveOrZero
    private Long calendarId;
    
	
    @NotNull
	@PositiveOrZero
    private Long workdayId;
    
    
    @NotBlank
    @DateTimeFormat(iso = ISO.DATE)
    private String startDate;
    
    
    @NotBlank
    @DateTimeFormat(iso = ISO.DATE)
    private String endDate;
}
