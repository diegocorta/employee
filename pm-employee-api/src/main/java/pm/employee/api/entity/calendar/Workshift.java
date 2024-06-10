package pm.employee.api.entity.calendar;

import java.io.Serializable;

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
@Table(name = "pm_workshift")
public class Workshift 
	extends AbstractCommonEntity<Long>
	implements Serializable {
	
	
	private static final long serialVersionUID = -6593577320500656613L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "workshift";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    
    @Column(name = "name",
            length = 40,
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "mane must not be null, nor empty")
    @Size(min = 3, max = 40, message = "name must contain between 3 and 40 characters")
    private String name;
    
    @Column(name = "code",
            length = 8,
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "code must not be null, nor empty")
    @Size(min = 2, max = 8, message = "code must contain between 2 and 8 characters")
    private String code;
    
    
    @Column(name = "description",
            length = 1000,
            nullable = true,
            updatable = true)
    private String description;
    
    
    @Column(name = "color",
            nullable = false,
            updatable = true,
            unique = false)
    private String color;
    
    
    @Column(name = "is_productive",
            nullable = false,
            updatable = true,
            unique = false)
    private boolean productive;
    
}
