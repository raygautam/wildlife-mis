package in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DivisionResponseDTO {
        private  Integer divisionId;
        private  String divisionName;
        private Long stateCode;
        private String stateName;
        private Long serviceId;
        private String serviceName;
}
