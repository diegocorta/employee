package pm.employee.common.dto.employee;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.validation.constraints.Email;
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
public class EmployeeDto 
		extends AbstractCommonDto {

	private static final long serialVersionUID = -8174400968500071552L;
	
	public static final String MINIMIZED_REL = "minimized";
	public static final String CONTRACTS_REL = "contracts";
	public static final String LAST_CONTRACT_REL = "last_contracts";
	public static final String GENDER_REL = "geneder";
	public static final String JOB_REL = "job";


	
	private Long id;
	
	
	@NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 20, message = "name must contain between 3 and 20 characters")
    private String name;
    
    
    @NotBlank(message = "first_surname must not be null, nor empty")
    @Size(min = 2, max = 40, message = "first_surname must contain between 2 and 40 characters")
    private String firstSurname;
    
    
    @Size(max = 40, message = "last_surname cant be greather than 40 characters")
    private String lastSurname;
    
    
    @DateTimeFormat(iso = ISO.DATE)
    @NotBlank(message = "birthdate must not be null, nor empty")
    private String birthdate;
    
    
    @NotBlank(message = "gender must not be null, nor empty")
    private String gender;
    
    
    @NotBlank(message = "job must not be null, nor empty")
    private String job;
    
    
    @NotBlank(message = "country must not be null, nor empty")
    private String country;
    
    
    @NotBlank(message = "cardId must not be null, nor empty")
    @Size(min = 8, max = 15, message = "cardId must contain between 8 and 15 characters")
    private String cardId;
    
    
    @NotBlank(message = "naf_id must not be null, nor empty")
    @Size(min = 8, max = 15, message = "naf_id must contain between 8 and 15 characters")
    private String nafId;
    
    
    @NotNull
    @Email(message = "Email is not valid")
    private String email;
    
    
    @PositiveOrZero
    @NotNull
    private Long securityUserId;
    
    
	private byte[] image;

    
	private String imageType;
	
	
    public String getFullName() {
    	
    	if (StringUtils.hasText(lastSurname)) {
        	return name.concat(" ").concat(firstSurname).concat(" ").concat(lastSurname);
    	} else {
        	return name.concat(" ").concat(firstSurname);
    	}
    }
    
}
