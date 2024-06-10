package pm.employee.api.entity.recording;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pm.employee.api.entity.employee.Employee;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_employee_journal")
public class EmployeeJournal 
	extends AbstractCommonEntity<Long>
	implements Serializable {

	
	private static final long serialVersionUID = 6544232179283330724L;


	/**
	 * Default object description for message composition
	 */
	public static final String DEFAULT_DESCRIPTION = "employee_journal";


	public static final String EMPLOYEE = "employee";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @EqualsAndHashCode.Exclude
    private Employee employee;
	
	
	@Column(name = "date",
	            nullable = false,
	            updatable = true,
	            unique = false)
	@NotNull
	@DateTimeFormat
	private LocalDate date;
	
	
    @Column(name = "timezone",
            nullable = false,
            updatable = true,
            unique = false)
    private TimeZone timezone;
    
    
	@Column(name = "journal_start",
            nullable = false,
            updatable = true,
            unique = false)
	@NotNull
	@DateTimeFormat
	private ZonedDateTime journalStart;
	
	
	@Column(name = "journal_end",
            nullable = true,
            updatable = true,
            unique = false)
	@DateTimeFormat
	private ZonedDateTime journalEnd;
	
}
