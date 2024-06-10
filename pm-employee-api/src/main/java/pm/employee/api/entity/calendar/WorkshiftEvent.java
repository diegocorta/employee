package pm.employee.api.entity.calendar;

import java.io.Serializable;
import java.time.LocalTime;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name = "pm_workshift_event")
public class WorkshiftEvent 
	extends AbstractCommonEntity<Long>
	implements Serializable {
	
	
	private static final long serialVersionUID = -6593577320500656613L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "workshift_event";


	public static final String WORKSHIFT = "workshift";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshift_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @EqualsAndHashCode.Exclude
    private Workshift workshift;
    
    
    @Column(name = "time",
            nullable = true,
            updatable = true,
            unique = false)
    private LocalTime time;
    
    
    @Column(name = "duration",
            nullable = false,
            updatable = true,
            unique = false)
    @PositiveOrZero
    private Long duration;
    
    
    @Column(name = "is_productive",
            nullable = false,
            updatable = true,
            unique = false)
    @PositiveOrZero
    private boolean productive;
    
}
