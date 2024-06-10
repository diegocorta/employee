package pm.employee.api.entity.recording;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
@Table(name = "pm_employee_journal_record")
public class EmployeeJournalRecord 
	extends AbstractCommonEntity<Long>
	implements Serializable {

	
	private static final long serialVersionUID = -2593294195046298672L;


	/**
	 * Default object description for message composition
	 */
	public static final String DEFAULT_DESCRIPTION = "employee_journal_record";


	public static final String RECORD_TYPE = "record_type";
	public static final String EMPLOYEE_JOURNAL = "employee_journal";
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_journal_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @EqualsAndHashCode.Exclude
    private EmployeeJournal employeeJournal;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_type_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @EqualsAndHashCode.Exclude
	private RecordType recordType;
	
	
	@Column(name = "instant",
            nullable = false,
            updatable = true,
            unique = false)
	@NotNull
	@DateTimeFormat
	private ZonedDateTime instant;
	
	
	@Column(name = "is_entry",
            nullable = false,
            updatable = true,
            unique = false)
    @NotNull
    private boolean entry;
	
	
}
