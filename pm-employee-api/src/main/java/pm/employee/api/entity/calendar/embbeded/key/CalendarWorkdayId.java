package pm.employee.api.entity.calendar.embbeded.key;

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
public class CalendarWorkdayId implements Serializable {

	
	private static final long serialVersionUID = -6136068394957861308L;


	@NotNull
    private Long workdayId;
    
    
    @NotNull
    private Long calendarId;
}
