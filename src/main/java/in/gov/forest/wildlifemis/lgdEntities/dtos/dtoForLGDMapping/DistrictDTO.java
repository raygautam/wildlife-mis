package in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictDTO{
    private Long districtCode;
    private String districtNameEnglish;
    private String districtNameLocal;
    private String census2001Code;
    private String census2011Code;
    private String sscode;
}
