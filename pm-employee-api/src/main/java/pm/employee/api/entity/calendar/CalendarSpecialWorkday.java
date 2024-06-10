package pm.employee.api.entity.calendar;

import java.io.Serializable;
import java.time.LocalDate;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_calendar_special_workday")
public class CalendarSpecialWorkday 
	extends AbstractCommonEntity<CalendarWorkdayId>
	implements Serializable {

	
	private static final long serialVersionUID = -1177554002385655557L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "calendar_special_workday";


	public static final String CALENDAR = "calendar";
	public static final String WORKDAY = "workday";	
	
    
    @EmbeddedId
    private CalendarWorkdayId id;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("calendarId")
    @EqualsAndHashCode.Exclude
    private Calendar calendar;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workday_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("workdayId")
    @EqualsAndHashCode.Exclude
    private Workday workday;
    
    
    @Column(name = "start_date",
            nullable = false,
            updatable = true,
            unique = false)
    @NotBlank(message = "start_date must not be null, nor empty")
    private LocalDate startDate;
    
    
    @Column(name = "end_date",
            nullable = true,
            updatable = true,
            unique = false)
    private LocalDate endDate;
    
    
}
