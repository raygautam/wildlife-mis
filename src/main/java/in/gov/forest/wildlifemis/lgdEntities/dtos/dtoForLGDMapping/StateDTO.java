package in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateDTO {
    private Long stateCode;
    private String stateNameEnglish;
    private String stateNameLocal;
    private String census2001Code;
    private String census2011Code;
}
