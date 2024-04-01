//package in.gov.forest.wildlifemis.config;
//
//import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
//import in.gov.forest.wildlifemis.domian.AppUser;
//import in.gov.forest.wildlifemis.domian.ForestService;
//import in.gov.forest.wildlifemis.domian.Role;
//import in.gov.forest.wildlifemis.forest_service.ForestServiceRepository;
//import in.gov.forest.wildlifemis.role.RoleRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//
//@Component
//@Slf4j
//public class RoleAndAppUserInitializer {
//    private final PasswordEncoder passwordEncoder;
//    private final AppUserManagementRepository appUserManagementRepository;
//    private final RoleRepository roleRepository;
//
//    private final ForestServiceRepository forestServiceRepository;
//
//    @Autowired
//    public RoleAndAppUserInitializer(PasswordEncoder passwordEncoder, AppUserManagementRepository appUserManagementRepository, RoleRepository roleRepository, ForestServiceRepository forestServiceRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.appUserManagementRepository = appUserManagementRepository;
//        this.roleRepository = roleRepository;
//        this.forestServiceRepository = forestServiceRepository;
//    }
//
//    @PostConstruct
//    @Transactional
//    public void addAppUserAndRole(){
////		Set<Role> roles=Set.of(
////				Role.builder().name("PCCF & HooF").build(),
////				Role.builder().name("PCCF").build(),
////				Role.builder().name("DFO").build(),
////				Role.builder().name("RFO").build(),
////              Role.builder().name("SuperAdmin").build(),
////              Role.builder().name("Admin").build()
////		);
////        Role.builder().name("RFO").build();
////		roleRepository.save( Role.builder().name("Admin").build());
////		roleRepository.saveAll(roles);
//
////        Set<ForestService> services= Set.of(
////                ForestService.builder().serviceName("wildlife_admin").build(),
////                ForestService.builder().serviceName("wildlife_territorial").build(),
////                ForestService.builder().serviceName("social_forestry").build()
////        );
////        forestServiceRepository.saveAll(services);
//
////
//
//
//        Set<Role> role=roleRepository.findByName("PCCF & HooF");
//        log.info("role : {}",role);
////        System.out.println("roleId : "+role.getId()+" , roleName : "+role.getName());
//        AppUser appUser=AppUser.builder()
//                .name("Admin")
//                .userName("admin@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .accountNonLocked(Boolean.TRUE)
//                .failedAttempt(0)
//                .isActive(Boolean.TRUE)
//                .lockTime(null)
//                .roles(role)
//                .service(null)
//                .division(null)
//                .range(null)
//                .build();
//        System.out.println("role : "+appUser.getRoles());
//        appUserManagementRepository.save(appUser);
//
//        Set<Role> role=roleRepository.findByName("Admin");
//        log.info("role : {}",role);
////        System.out.println("roleId : "+role.getId()+" , roleName : "+role.getName());
//        AppUser appUser=AppUser.builder()
//                .name("Admin")
//                .userName("admin@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .accountNonLocked(Boolean.TRUE)
//                .failedAttempt(0)
//                .isActive(Boolean.TRUE)
//                .lockTime(null)
//                .roles(role)
//                .service(null)
//                .division(null)
//                .range(null)
//                .build();
//        System.out.println("role : "+appUser.getRoles());
//        appUserManagementRepository.save(appUser);
//
//
//    }
//}
