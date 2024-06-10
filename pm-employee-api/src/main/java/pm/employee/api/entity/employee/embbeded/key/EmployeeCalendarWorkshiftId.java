package pm.employee.api.entity.employee.embbeded.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeCalendarWorkshiftId implements Serializable {
	
	private static final long serialVersionUID = 682327947760951653L;

	@NotNull
    private Long employeeId;
    
    
	@NotNull
    private Long calendarId;
	
	
    @NotNull
    private Long workshiftId;
    
}
