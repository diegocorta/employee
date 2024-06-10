package pm.employee.api.entity.calendar;

import java.io.Serializable;
import java.time.LocalDate;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "pm_calendar")
public class Calendar 
	extends AbstractCommonEntity<Long>
	implements Serializable {	
	
	
	private static final long serialVersionUID = 798599896915618610L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "calendar";	
	
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    
    @Column(name = "name",
            length = 40,
            nullable = false,
            updatable = true,
            unique = true)
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 40, message = "name must contain between 3 and 40 characters")
    private String name;
    
    
    @Column(name = "description",
            length = 1000,
            nullable = true,
            updatable = true)
    private String description;
    
    
    @Column(name = "start_date",
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "birthdate must not be null, nor empty")
    private LocalDate startDate;
    
    
    @Column(name = "end_date",
            nullable = true,
            updatable = true,
            unique = false)
    private LocalDate endDate;
    
    
}
