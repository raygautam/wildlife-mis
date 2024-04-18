package in.gov.forest.wildlifemis.credential.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private String userName;
    private String existingPassword;
    private String newPassword;
}
