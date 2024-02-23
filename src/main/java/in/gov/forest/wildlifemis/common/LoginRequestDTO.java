package in.gov.forest.wildlifemis.common;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userName;
    private String password;
}
