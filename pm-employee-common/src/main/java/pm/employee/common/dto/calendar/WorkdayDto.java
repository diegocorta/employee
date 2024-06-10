package pm.employee.common.dto.calendar;

import java.util.TimeZone;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "workdays")
public class WorkdayDto 
	extends AbstractCommonDto {

	private static final long serialVersionUID = 5536521929412871197L;


	public static final String WORKSHIFT_REL = "workshifts";


	private Long id;
    
    
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 40, message = "name must contain between 3 and 40 characters")
    private String name;
    
    
    @Nullable
    @Size(max = 1000)
    private String description;
    
    
    @NotNull
    @Valid
    private TimeZone timezone;
    
    
    @NotNull    
    private boolean startsPreviousDay;
}
