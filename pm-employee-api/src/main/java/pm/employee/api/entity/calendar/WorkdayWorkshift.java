package pm.employee.api.entity.calendar;

import java.io.Serializable;

import es.common.entity.AbstractCommonEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pm.employee.api.entity.calendar.embbeded.key.WorkdayWorkshiftId;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pm_workday_workshift")
public class WorkdayWorkshift 
	extends AbstractCommonEntity<WorkdayWorkshiftId>
	implements Serializable {

	
	private static final long serialVersionUID = 7872876972982775138L;


	public static final String WORKSHIFT = "workshift";
	public static final String WORKDAY = "workday";


	@EmbeddedId
    private WorkdayWorkshiftId id;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workday_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("workdayId")
    @EqualsAndHashCode.Exclude
    private Workday workday;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshift_id",
			foreignKey = @ForeignKey(),
			nullable = false,
			updatable = false)
    @MapsId("workshiftId")
    @EqualsAndHashCode.Exclude
    private Workshift workshift;
    
}
