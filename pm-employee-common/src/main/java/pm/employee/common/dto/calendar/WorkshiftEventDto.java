package pm.employee.common.dto.calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.common.dto.AbstractCommonDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "workshiftEvents")
public class WorkshiftEventDto 
	extends AbstractCommonDto {

	
	private static final long serialVersionUID = 6261978679013748671L;


	private Long id;
    
    
    @NotNull
    @PositiveOrZero
    private Long workshiftId;
    
    
    @Nullable
    @DateTimeFormat(iso = ISO.TIME)
    private String time;
    
    
    @NotNull
    @PositiveOrZero
    private Long duration;
    
    
    @NotNull
    private boolean productive;
    
}
