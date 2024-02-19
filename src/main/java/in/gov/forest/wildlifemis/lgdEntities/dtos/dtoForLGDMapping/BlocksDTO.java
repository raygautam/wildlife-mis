package in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlocksDTO {
    private Long blockCode;
    private String blockNameEnglish;
    private String blockNameLocal;
//        private Long subdistrictCode;
//        private String subdistrictNameEnglish;
//        private String subdistrictNameLocal;
//        private String census2001Code;
//        private String census2011Code;
//        private String sscode;
}
