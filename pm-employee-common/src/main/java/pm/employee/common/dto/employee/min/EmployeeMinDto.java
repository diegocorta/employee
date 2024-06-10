package pm.employee.common.dto.employee.min;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
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
@Relation(collectionRelation = "employees")
public class EmployeeMinDto 
		extends AbstractCommonDto {

	
	private static final long serialVersionUID = -6733025306188891004L;


	private Long id;
	
	
	@NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 20, message = "name must contain between 3 and 20 characters")
    private String name;
    
    
    @NotBlank(message = "first_surname must not be null, nor empty")
    @Size(min = 2, max = 40, message = "first_surname must contain between 2 and 20 characters")
    private String firstSurname;
    
    
    @Size(min = 2, max = 40, message = "first_surname must contain between 2 and 20 characters")
    private String lastSurname;
    
    
    @NotBlank(message = "cardId must not be null, nor empty")
    @Size(min = 8, max = 15, message = "cardId must contain between 8 and 15 characters")
    private String cardId;
    
    
    @PositiveOrZero
    @NotNull
    private Long securityUserId;
    
    
    public String getFullName() {
    	
    	if (lastSurname != null) {
        	return name.concat(" ").concat(firstSurname).concat(" ").concat(lastSurname);
    	} else {
        	return name.concat(" ").concat(firstSurname);
    	}
    }
    
}
