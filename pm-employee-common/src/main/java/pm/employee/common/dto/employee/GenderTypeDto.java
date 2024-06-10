package pm.employee.common.dto.employee;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "genders")
public class GenderTypeDto 
		extends AbstractCommonDto {

	
	private static final long serialVersionUID = -290519828894106161L;


	private Long id;
	
	
	@NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 20, message = "name must contain between 3 and 20 characters")
    private String name;
    
}
