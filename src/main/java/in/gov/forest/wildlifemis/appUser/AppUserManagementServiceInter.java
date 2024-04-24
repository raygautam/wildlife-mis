package in.gov.forest.wildlifemis.appUser;


import in.gov.forest.wildlifemis.appUser.dto.AppUserManagementDto;
import in.gov.forest.wildlifemis.common.ApiResponse;

public interface AppUserManagementServiceInter {
    ApiResponse<?> insertAppUser(AppUserManagementDto userDetailDto);

    ApiResponse<?> get();

//    List<GetAppUserDto> getUserDetails();
//
//    List<GetUserIdAndRoleNameDTO> getUserIdAndRoleName();
}
