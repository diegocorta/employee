package pm.employee.common.dto.calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "calendars")
public class CalendarDto 
	extends AbstractCommonDto {

	
    private static final long serialVersionUID = 4208395915401101242L;


	private Long id;
    
    
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 40, message = "name must contain between 3 and 40 characters")
    private String name;
    

    @Nullable
    @Size(max = 1000)
    private String description;
    
    
    @NotBlank
    @DateTimeFormat(iso = ISO.DATE)
    private String startDate;
    
    
    @Nullable
    @DateTimeFormat(iso = ISO.DATE)
    private String endDate;
    
    
}
