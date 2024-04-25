package in.gov.forest.wildlifemis.role;

//import com.example.jwt.jwtauthenticationdemo.Uppercase;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleDto {
    private  Integer roleId;
    private  String roleName;
}