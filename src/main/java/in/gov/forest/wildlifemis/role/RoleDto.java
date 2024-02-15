package in.gov.forest.wildlifemis.role;

//import com.example.jwt.jwtauthenticationdemo.Uppercase;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RoleDto {
    private  Integer roleId;
    private  String roleName;
}