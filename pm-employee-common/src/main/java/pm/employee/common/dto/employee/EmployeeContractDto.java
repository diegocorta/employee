package pm.employee.common.dto.employee;

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
@Relation(collectionRelation = "employeContracts")
public class EmployeeContractDto extends AbstractCommonDto {

	
	private static final long serialVersionUID = 7998566382440786529L;
	public static final String MINIMIZED_REL = "minimized";
	public static final String CONTRACT_TYPE_REL = "contractTypes";
	public static final String EMPLOYEES_REL = "employees";
	
	private Long id;
	
	@NotNull
	private Long contractTypeId;
	
	
	private String contractTypeName;
	
	@NotNull
	private Long employeeId;
	
	@DateTimeFormat(iso = ISO.DATE)
	private String contractStart;

	@DateTimeFormat(iso = ISO.DATE)
	@Nullable
	private String contractEnd;
	
    @PositiveOrZero
    @NotNull
	private Long weekWorkHours;
}
