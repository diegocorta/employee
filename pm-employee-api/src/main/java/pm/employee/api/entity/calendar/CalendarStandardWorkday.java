package pm.employee.api.entity.calendar;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import lombok.extern.apachecommons.CommonsLog;
import pm.employee.api.entity.calendar.embbeded.key.CalendarWorkdayId;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_calendar_standard_workday")
@CommonsLog
public class CalendarStandardWorkday 
	extends AbstractCommonEntity<CalendarWorkdayId>
	implements Serializable {

	
	private static final long serialVersionUID = -1177554002385655557L;


	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "calendar_standard_workday";	
	
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
    
    
    @Column(name = "weekdays",
            length = 15,
            nullable = true,
            updatable = true)
    private String weekdays;
    
    
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
    

    public List<DayOfWeek> getWeekdays() {
    	
    	try {
    		
    		return List.of(weekdays.split(";"))
    	    		.stream()
    	    		.map(Integer::valueOf)
    	    		.map(DayOfWeek::of)
    	    		.collect(Collectors.toCollection(ArrayList::new));
    		
    	} catch (Exception e) {
    		
    		log.error("data broken for calendar_standar_workday with id: " + id + ". the days of the week could not be extracted");
    		throw e;
    		
    	}
    	
    }
    
    
    public void setWeekdays(List<DayOfWeek> weekdays) {
    	
    	this.weekdays = weekdays.stream()
    			.map(DayOfWeek::getValue)
                .map(String::valueOf)
                .collect(Collectors.joining(";"));
    }
    
}
