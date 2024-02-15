//package in.gov.forest.wildlifemis.appUser;
//import in.gov.forest.wildlifemis.domian.AppUser;
//import in.gov.forest.wildlifemis.domian.Role;
//import in.gov.forest.wildlifemis.role.RoleRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AppUserManagementServiceImpl implements AppUserManagementServiceInter {
//
////    @Autowired
////    private ModelMapper modelMapper;
//    @Autowired
//    private AppUserManagementRepository appUserRepository;
//
////    @Autowired
////    private ServiceRepository serviceRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public String insertAppUser(AppUserManagementDto appUserManagementDto)  {
//
//        return appUserRepository.save(ConvertAppUserDtoToAppUser(appUserManagementDto)).getUserName();
//    }
//
//
//    public AppUser ConvertAppUserDtoToAppUser(AppUserManagementDto appUserDto)  {
//        appUserDto.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
//        log.info("UserDetailDto object : {}",appUserDto);
//
//        AppUser appUser = new AppUser();
//        appUser.setId(appUserDto.getId());
//        appUser.setUserName(appUserDto.getUserName().toUpperCase());
//        appUser.setName(appUserDto.getName());
//        appUser.setPassword(appUserDto.getPassword());
//        // convert role IDs to a set of Role_m objects
//        Set<Role> roles = new HashSet<>();
//        for (Integer roleId : appUserDto.getRoleId()) {
//            roles.add(roleRepository.getReferenceById(roleId));
//        }
//        appUser.setRoles(roles);
////        appUser.setService(serviceRepository.getReferenceById(appUserDto.getService()));
//        appUser.setAccountNonLocked(Boolean.TRUE);
//        appUser.setLockTime(null);
//        appUser.setFailedAttempt(0);
//
////               userDetail_t.s
//
//        log.info("userDetail_t object : {}",appUser);
//        return appUser;
//    }
//
//
//    //filter stream example
////    @Override
////    public List<GetUserDetailDto> getUserDetails() {
////        List<GetUserDetailDto> getUserDetailDto = jpaStreamer.stream(UserDetail_t.class)
////                .map(userDetail_t -> modelMapper.map(userDetail_t, GetUserDetailDto.class))
////                .collect(Collectors.toList());
////
////        return getUserDetailDto.stream()
////                .filter(getUserDetailDto1 ->getUserDetailDto1.getRoleName().contentEquals("RFO") )
////                .collect(Collectors.toList());
////    }
//
////    ->open it
////    @Override
////    public List<GetAppUserDto> getAppUser() {
//////        return jpaStreamer.stream(UserDetail_t.class)
//////                .map(userDetail_t -> modelMapper.map(userDetail_t, GetUserDetailDto.class))
//////                .collect(Collectors.toList());
////
////        return userDetailTRepository.findAll().stream()
////                .map(this::covertToGetUserDetailDto)
////                .collect(Collectors.toList());
////    }
////
////    public GetAppUserDto covertToGetUserDetailDto(AppUser userDetail_t){
////        GetAppUserDto getUserDetailDto=new GetAppUserDto();
////        getUserDetailDto.setUserId(userDetail_t.getUserId());
////        getUserDetailDto.setRoleName(userDetail_t.getRoleId());
//////        getUserDetailDto.setLevelType(userDetail_t.getLevelType());
//////        getUserDetailDto.setLevelName(userDetail_t.getLevelName());
////        return getUserDetailDto;
////    }
////
////    @Override
////    public List<GetUserIdAndRoleNameDTO> getUserIdAndRoleName() {
//////        return jpaStreamer.stream(UserDetail_t.class)
//////                .map(userDetail_t -> convertToGetUserDetailDto(userDetail_t))
//////                .collect(Collectors.toList());
////        return userDetailTRepository.findAll().stream()
////                .map(this::convertToGetUserDetailDto)
////                .collect(Collectors.toList());
////    }
////
////    public GetUserIdAndRoleNameDTO convertToGetUserDetailDto(AppUser userDetail_t){
////        GetUserIdAndRoleNameDTO getUserIdAndRoleNameDTO=new GetUserIdAndRoleNameDTO();
//////        if(userDetail_t.getRoleM().getRoleName().contains("DFO")
//////        || userDetail_t.getRoleM().getRoleName().contains("DHO")
//////        || userDetail_t.getRoleM().getRoleName().contains("DVO")){
//////            getUserIdAndRoleNameDTO.setLabel(userDetail_t.getLevelName());
////            getUserIdAndRoleNameDTO.setValue(userDetail_t.getUserId());
//////        }
////        return getUserIdAndRoleNameDTO;
////    }
//
//
////    @Override
////    public List<GetUserDetailDto> getUserDetails
//}
