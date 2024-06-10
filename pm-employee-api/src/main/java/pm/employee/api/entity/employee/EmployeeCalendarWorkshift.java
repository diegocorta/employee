package pm.employee.api.entity.employee;

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
import pm.employee.api.entity.calendar.Calendar;
import pm.employee.api.entity.calendar.Workshift;
import pm.employee.api.entity.employee.embbeded.key.EmployeeCalendarWorkshiftId;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_employee_calendar_workshift")
public class EmployeeCalendarWorkshift 
	extends AbstractCommonEntity<EmployeeCalendarWorkshiftId>
	implements Serializable {

	
	private static final long serialVersionUID = 1083166237637704605L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "employee_calendar_workshift";


	public static final String EMPLOYEE = "employee";
	public static final String CALENDAR = "calendar";
	public static final String WORKSHIFT = "workshift";
	
	
	@EmbeddedId
    private EmployeeCalendarWorkshiftId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("employeeId")
    @EqualsAndHashCode.Exclude
    private Employee employee;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("calendarId")
    @EqualsAndHashCode.Exclude
    private Calendar calendar;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshift_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("workshiftId")
    @EqualsAndHashCode.Exclude
    private Workshift workshift;
    
    
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
