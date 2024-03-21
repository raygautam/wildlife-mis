package in.gov.forest.wildlifemis.servicePlus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicePlusDistrictWiseAnalyticsFetchDTO {
    private String submitted;
    private String rejected;
    private String delivered;
    private String pending;


}
