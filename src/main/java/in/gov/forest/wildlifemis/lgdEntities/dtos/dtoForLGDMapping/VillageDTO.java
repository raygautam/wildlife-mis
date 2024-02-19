package in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageDTO {
    private Long villageCode;
    private String villageNameEnglish;
    private String villageNameLocal;
    private String census2001Code;
    private String census2011Code;
    private String sscode;
}
