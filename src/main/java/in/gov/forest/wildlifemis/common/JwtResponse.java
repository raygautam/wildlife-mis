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
    private  Long serviceId;
    private  Integer rangeId;
    private List<String> roles;
//    private Boolean isActive;
//    private  String stateName;
}

