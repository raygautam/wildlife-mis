package in.gov.forest.wildlifemis.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type;
    private String refreshToken;
    private Long id;
    private String userName;
    private  Integer divisionId;
    private  String divisionName;
    private  Long serviceId;
    private  String serviceName;
    private  Integer rangeId;
    private  String rangeName;
    private List<String> roles;
//    private Boolean isActive;
//    private  String stateName;
}

