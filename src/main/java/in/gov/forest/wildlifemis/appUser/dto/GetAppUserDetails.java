package in.gov.forest.wildlifemis.appUser.dto;

import lombok.*;

import java.util.Set;

//@Builder
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class GetAppUserDetails {
//    private String name;
//    private Boolean isActive;
//    private Set<?> roles;
//    private String service;
//    private String division;
//    private String range;
//}

public record GetAppUserDetails(Long Id,String userName,Boolean isActive,Set<?> roles,String service,String division,String range) {
}
