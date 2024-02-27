package in.gov.forest.wildlifemis.servicePlus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    String serviceName;
    Integer applied;
    Integer delivered;
    Integer rejected;
    Integer pending;

}
