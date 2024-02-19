package in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberOfDistrictDTO {
    private Long total;
    private Long districtCode;
    private String districtName;
}
