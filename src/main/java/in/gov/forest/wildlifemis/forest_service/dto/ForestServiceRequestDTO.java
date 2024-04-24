package in.gov.forest.wildlifemis.forest_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ForestServiceRequestDTO {

    private Long id;
    private String serviceName;
    private String serviceId;
}
