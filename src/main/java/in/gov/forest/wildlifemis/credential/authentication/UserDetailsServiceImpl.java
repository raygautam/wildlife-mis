package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementDto;
import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private AppUserManagementRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   AppUser appUser = appUserRepository.findByUserNameAndIsActive(username, Boolean.TRUE)
           .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));;
    log.info("AppUser : "+appUser);

//      AppUserManagementDto.builder()
//              .service(appUser.getService().getServiceName())
//              .roleId(appUser.getRoles())
           return UserDetailsImpl.build(appUser);
  }

}
