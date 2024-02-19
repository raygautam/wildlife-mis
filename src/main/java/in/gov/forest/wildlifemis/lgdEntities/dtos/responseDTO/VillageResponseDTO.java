package in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageResponseDTO {
    private Long villageCode;
    private String villageName;
    private Long subDistrictCode;
    private String  subDistrictName;
}
