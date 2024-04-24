package in.gov.forest.wildlifemis.appUser;
import in.gov.forest.wildlifemis.appUser.dto.AppUserManagementDto;
import in.gov.forest.wildlifemis.appUser.dto.GetAppUserDetails;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.domian.ForestService;
import in.gov.forest.wildlifemis.domian.Role;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.forest_service.ForestServiceRepository;
import in.gov.forest.wildlifemis.lgdEntities.entities.Division;
import in.gov.forest.wildlifemis.lgdEntities.entities.Range;
import in.gov.forest.wildlifemis.lgdEntities.repository.DivisionRepository;
import in.gov.forest.wildlifemis.lgdEntities.repository.RangeRepository;
import in.gov.forest.wildlifemis.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppUserManagementServiceImpl implements AppUserManagementServiceInter {

//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private AppUserManagementRepository appUserManagementRepository;

    @Autowired
    private ForestServiceRepository forestServiceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private RangeRepository rangeRepository;


    private final PasswordEncoder passwordEncoder;
    public AppUserManagementServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse<?> insertAppUser(AppUserManagementDto appUserManagementDto)  {

        try{
            if (appUserManagementRepository.existsByUserName(appUserManagementDto.getUserName())) {
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(Collections.singletonList(new Error("", "Username already exists")))
                        .build();
            }

            Set<Role> roles = new HashSet<>();
            for(Integer role : appUserManagementDto.getRoleId()){
//                Role role1=roleRepository.findById(role).orElseThrow(()->new RuntimeException("Role not found"));
//                assert false;
//                log.info("Role: {}",role1);
                roles.add(roleRepository.getReferenceById(role));
            }
            ForestService forestService=null;
            Division division=null;
            Range range=null;
            if(appUserManagementDto.getService()!=null){
                forestService=forestServiceRepository.getReferenceById(appUserManagementDto.getService());
            }
            if(appUserManagementDto.getDivision()!=null){
                division=divisionRepository.getReferenceById(appUserManagementDto.getDivision());
            }
            if(appUserManagementDto.getRange()!=null){
                range=rangeRepository.getReferenceById(appUserManagementDto.getRange());
            }
            appUserManagementRepository.save(
                    AppUser.builder()
                            .name(appUserManagementDto.getName())
                            .userName(appUserManagementDto.getUserName())
                            .password(passwordEncoder.encode(appUserManagementDto.getPassword()))
                            .accountLocked(Boolean.FALSE)
                            .failedLoginAttempts(0)
                            .isActive(Boolean.TRUE)
                            .lockoutTime(null)
                            .roles(roles)
                            .service(forestService)
                            .division(division)
                            .range(range)
                            .build()
            );
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            "Data Inserted Successfully"
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to insert", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try {

            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                        appUserManagementRepository.findAll()
                                .stream()
                                .map(appUser -> {
                                    return new GetAppUserDetails(
                                            appUser.getName(),
                                            appUser.getIsActive(),
                                            appUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                                            appUser.getService().getServiceName(),
                                            appUser.getDivision()!=null?appUser.getDivision().getName():null,
                                            appUser.getRange()!=null?appUser.getRange().getRangeName():null
                                    );
                                }).collect(Collectors.toList())
                    )
                    .build();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException("Failed to retrieve", new Error("",e.getMessage()));
        }
    }


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


    //filter stream example
//    @Override
//    public List<GetUserDetailDto> getUserDetails() {
//        List<GetUserDetailDto> getUserDetailDto = jpaStreamer.stream(UserDetail_t.class)
//                .map(userDetail_t -> modelMapper.map(userDetail_t, GetUserDetailDto.class))
//                .collect(Collectors.toList());
//
//        return getUserDetailDto.stream()
//                .filter(getUserDetailDto1 ->getUserDetailDto1.getRoleName().contentEquals("RFO") )
//                .collect(Collectors.toList());
//    }

//    ->open it
//    @Override
//    public List<GetAppUserDto> getAppUser() {
////        return jpaStreamer.stream(UserDetail_t.class)
////                .map(userDetail_t -> modelMapper.map(userDetail_t, GetUserDetailDto.class))
////                .collect(Collectors.toList());
//
//        return userDetailTRepository.findAll().stream()
//                .map(this::covertToGetUserDetailDto)
//                .collect(Collectors.toList());
//    }
//
//    public GetAppUserDto covertToGetUserDetailDto(AppUser userDetail_t){
//        GetAppUserDto getUserDetailDto=new GetAppUserDto();
//        getUserDetailDto.setUserId(userDetail_t.getUserId());
//        getUserDetailDto.setRoleName(userDetail_t.getRoleId());
////        getUserDetailDto.setLevelType(userDetail_t.getLevelType());
////        getUserDetailDto.setLevelName(userDetail_t.getLevelName());
//        return getUserDetailDto;
//    }
//
//    @Override
//    public List<GetUserIdAndRoleNameDTO> getUserIdAndRoleName() {
////        return jpaStreamer.stream(UserDetail_t.class)
////                .map(userDetail_t -> convertToGetUserDetailDto(userDetail_t))
////                .collect(Collectors.toList());
//        return userDetailTRepository.findAll().stream()
//                .map(this::convertToGetUserDetailDto)
//                .collect(Collectors.toList());
//    }
//
//    public GetUserIdAndRoleNameDTO convertToGetUserDetailDto(AppUser userDetail_t){
//        GetUserIdAndRoleNameDTO getUserIdAndRoleNameDTO=new GetUserIdAndRoleNameDTO();
////        if(userDetail_t.getRoleM().getRoleName().contains("DFO")
////        || userDetail_t.getRoleM().getRoleName().contains("DHO")
////        || userDetail_t.getRoleM().getRoleName().contains("DVO")){
////            getUserIdAndRoleNameDTO.setLabel(userDetail_t.getLevelName());
//            getUserIdAndRoleNameDTO.setValue(userDetail_t.getUserId());
////        }
//        return getUserIdAndRoleNameDTO;
//    }


//    @Override
//    public List<GetUserDetailDto> getUserDetails
}
