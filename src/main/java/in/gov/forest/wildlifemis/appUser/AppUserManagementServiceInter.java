package in.gov.forest.wildlifemis.appUser;


import in.gov.forest.wildlifemis.common.ApiResponse;

public interface AppUserManagementServiceInter {
    ApiResponse<?> insertAppUser(AppUserManagementDto userDetailDto);

//    List<GetAppUserDto> getUserDetails();
//
//    List<GetUserIdAndRoleNameDTO> getUserIdAndRoleName();
}
