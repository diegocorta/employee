package pm.employee.api.entity.employee;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import es.common.entity.AbstractCommonEntity;
import jakarta.annotation.Nullable;
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
@Table(name = "pm_employee_contract")
public class EmployeeContract 
	extends AbstractCommonEntity<Long>
	implements Serializable {

	private static final long serialVersionUID = 8936422766977177147L;

	/**
     * Default object description for message composition
     */
    public static final String DEFAULT_DESCRIPTION = "employee_contract";
    
    public static final String EMPLOYEE = "employee";
    public static final String CONTRACT = "contract";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id",
			foreignKey = @ForeignKey(name = "fk_contract_employee_contract"),
			nullable = false,
			updatable = true)
    @EqualsAndHashCode.Exclude
    private ContractType contract;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",
			foreignKey = @ForeignKey(name = "fk_employee_employee_contract"),
			nullable = false,
			updatable = false)
    @EqualsAndHashCode.Exclude
    private Employee employee;
    
    
    @Column(name = "contract_start",
            nullable = false,
            updatable = true,
            unique = false)
    @EqualsAndHashCode.Exclude
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate contractStart;
    
    
    @Column(name = "contract_end",
            nullable = true,
            updatable = true,
            unique = false)
    @Nullable
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate contractEnd;
    
    
    @Column(name = "week_work_hours",
            nullable = false,
            updatable = true,
            unique = false)
    @PositiveOrZero
    private Long weekWorkHours;
    
}
