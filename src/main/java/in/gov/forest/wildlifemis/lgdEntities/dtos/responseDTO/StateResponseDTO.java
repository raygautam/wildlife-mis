package in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateResponseDTO {
    private Long stateCode;
    private String stateName;
}
