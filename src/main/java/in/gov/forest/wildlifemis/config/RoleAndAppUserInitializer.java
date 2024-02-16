package in.gov.forest.wildlifemis.config;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.domian.Role;
import in.gov.forest.wildlifemis.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@Slf4j
public class RoleAndAppUserInitializer {
    private final PasswordEncoder passwordEncoder;
    private final AppUserManagementRepository appUserManagementRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleAndAppUserInitializer(PasswordEncoder passwordEncoder, AppUserManagementRepository appUserManagementRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appUserManagementRepository = appUserManagementRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void addAppUser(){
//		Set<Role> roles=Set.of(
//				Role.builder().name("PCCF & HooF").build(),
//				Role.builder().name("PCCF").build(),
//				Role.builder().name("DFO").build(),
//				Role.builder().name("RFO").build()
//		);
//		roleRepository.saveAll(roles);

//		Role roleOptional = roles.stream()
//				.filter(role -> role.getName().equals("PCCF & HooF"))
//				.findFirst().orElse(null);

        Set<Role> role=roleRepository.findByName("PCCF & HooF");
        log.info("role : {}",role);
//        System.out.println("roleId : "+role.getId()+" , roleName : "+role.getName());
        AppUser appUser=AppUser.builder()
                .name("sachin")
                .userName("sachin@gmail.com")
                .password(passwordEncoder.encode("123"))
                .accountNonLocked(Boolean.TRUE)
                .failedAttempt(0)
                .isActive(Boolean.TRUE)
                .lockTime(null)
                .roles(role)
                .build();
        System.out.println("role : "+appUser.getRoles());
		appUserManagementRepository.save(appUser);
    }
}
