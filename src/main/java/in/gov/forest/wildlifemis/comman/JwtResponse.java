package in.gov.forest.wildlifemis.comman;

import in.gov.forest.wildlifemis.domian.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
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

