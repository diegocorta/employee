package pm.employee.common.dto.calendar;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "workdayWorkshifts")
public class WorkdayWorkshiftDto 
	extends AbstractCommonDto {

	private static final long serialVersionUID = -2761029151908456610L;


	@NotNull
	@PositiveOrZero
    private Long workdayId;
    
    
    @NotNull
    @PositiveOrZero
    private Long workshiftId;
	
}
