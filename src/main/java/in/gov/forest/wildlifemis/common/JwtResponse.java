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
    private String username;
    private  String divisionName;
    private  String serviceName;
    private  String rangeName;
    private List<String> roles;
//    private Boolean isActive;
//    private  String stateName;
}

