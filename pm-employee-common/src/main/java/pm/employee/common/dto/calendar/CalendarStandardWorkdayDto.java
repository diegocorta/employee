package pm.employee.common.dto.calendar;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "calendarStandardWorkdays")
public class CalendarStandardWorkdayDto 
	extends AbstractCommonDto {

	private static final long serialVersionUID = -8241851974558314860L;


	@NotNull
	@PositiveOrZero
    private Long calendarId;
    
	
    @NotNull
	@PositiveOrZero
    private Long workdayId;
    
    
    @Size(min = 1, max = 7, message = "the weekdays list must have between 1 and 7 elements")
    private List<@Min(value = 1, message = "number must be at least one. 1 will represent monday") 
    	@Max(value = 7, message = "number must be maximum seven. 7 will represent sunday")
    	Integer> weekdays;
   
    @NotBlank
    @DateTimeFormat(iso = ISO.DATE)
    private String startDate;
    
    
    @Nullable
    @DateTimeFormat(iso = ISO.DATE)
    private String endDate;
}

