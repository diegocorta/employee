package pm.employee.api.entity.recording;

import java.io.Serializable;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "pm_record_type")
public class RecordType 
	extends AbstractCommonEntity<Long>
	implements Serializable {
	
	
	private static final long serialVersionUID = -1961261937679485306L;


	/**
	 * Default object description for message composition
	 */
	public static final String DEFAULT_DESCRIPTION = "record_type";
	
	
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
	
	
	@Column(name = "description",
            length = 1000,
            nullable = true,
            updatable = true)
    private String description;
	
	
	@Column(name = "is_productive",
            nullable = false,
            updatable = true,
            unique = false)
    @NotNull
    private boolean productive;
	
	
	@Column(name = "is_default",
            nullable = false,
            updatable = true,
            unique = false)
    @NotNull
    private boolean defaultType;
	
	
}
