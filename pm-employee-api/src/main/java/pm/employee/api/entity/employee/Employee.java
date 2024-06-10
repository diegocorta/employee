package pm.employee.api.entity.employee;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_employee")
public class Employee 
		extends AbstractCommonEntity<Long>
		implements Serializable {

	
	private static final long serialVersionUID = -1371469569730646301L;

	
	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "employee";
	
    public static final String GENDER = "gender";
    public static final String JOB = "job";

    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    
    @Column(name = "name",
            length = 20,
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 20, message = "name must contain between 3 and 20 characters")
    private String name;
    
    
    @Column(name = "first_surname",
            length = 40,
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "first_surname must not be null, nor empty")
    @Size(min = 2, max = 40, message = "first_surname must contain between 2 and 20 characters")
    private String firstSurname;
    
    
    @Column(name = "last_surname",
            length = 40,
            nullable = true,
            updatable = true,
            unique = false)
    @Size(max = 40, message = "first_surname must contain a maximum of 40 characters")
    private String lastSurname;
    
    
    @Column(name = "birthdate",
            nullable = false,
            updatable = true,
            unique = false)
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthdate;
    
    
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
	private GenderType gender;
    
    
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
	private JobType job;
    
    
    @Column(name = "country",
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "country must not be null, nor empty")
    private String country;
    
    
    @Column(name = "card_id",
            length = 15,
            nullable = false,
            updatable = true,
            unique = true)
    @NotBlank(message = "cardId must not be null, nor empty")
    @Size(min = 8, max = 15, message = "cardId must contain between 8 and 15 characters")
    private String cardId;
    
    
    @Column(name = "naf_id",
            length = 15,
            nullable = false,
            updatable = true,
            unique = true)
    @NotBlank(message = "naf_id must not be null, nor empty")
    @Size(min = 8, max = 15, message = "naf_id must contain between 8 and 15 characters")
    private String nafId;
    
    
    @Column(name = "email",
            length = 50,
            nullable = false,
            updatable = true,
            unique = true)
    @Email(message = "Email is not valid")
    private String email;
    
    
    @Column(name = "security_user_id",
            nullable = false,
            updatable = false,
            unique = true)
    @PositiveOrZero
    private Long securityUserId;
    
    
    @Column(name = "img", nullable = true)
	private byte[] image;
	
    
    @Column(name = "img_type", nullable = true)
	private String imageType;

    
}
