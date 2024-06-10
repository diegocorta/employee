package pm.employee.common.dto.calendar;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
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
@Relation(collectionRelation = "workshifts")
public class WorkshiftDto 
	extends AbstractCommonDto {
	
	private static final long serialVersionUID = -3835961586141088213L;


	public static final String EVENTS_REL = "events";


	private Long id;
    
    
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 40, message = "name must contain between 3 and 40 characters")
    private String name;
    
    @NotBlank(message = "code must not be null, nor empty")
    @Size(min = 2, max = 8, message = "code must contain between 2 and 8 characters")
    private String code;

    @Nullable
    @Size(max = 1000)
    private String description;
    
    
    @Size(max = 10)
    private String color;
    
    
    @NotNull
    private boolean productive;

	
}
