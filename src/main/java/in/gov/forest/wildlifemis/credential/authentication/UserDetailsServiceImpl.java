package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private AppUserManagementRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//   AppUser serviceIsActive=appUserRepository.findByUserNameAndServiceIsActive(username, Boolean.TRUE);
//   if(serviceIsActive==null){
//       throw new AccessDeniedException("Service is deactivated ");
//   }
   AppUser appUser = appUserRepository.findByUserNameAndIsActive(username, Boolean.TRUE)
           .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));;
    log.info("AppUser : "+appUser);

//    appUser.getRoles().stream()
//            .map(role-> {
//                        if(!role.getName().contains("SUPER_ADMIN") || !role.getName().contains("ADMIN")) {
//                            if(appUser.getService().getIsActive())
//                            {
//                                return UserDetailsImpl.build(appUser);
//                            }else{
//                                throw new AccessDeniedException("Service is deactivated");
//                            }
//                        }
//                        return UserDetailsImpl.build(appUser);
//                    }
//            );


//      return appUser.getRoles().stream()
//              .filter(role -> !role.getName().contains("SUPER_ADMIN") && !role.getName().contains("ADMIN"))
//              .findFirst() // Assuming you want to return UserDetails for the first matching role
//              .map(role -> {
//                  if (appUser.getService().getIsActive()) {
//                      return UserDetailsImpl.build(appUser);
//                  } else {
//                      throw new AccessDeniedException("Service is deactivated");
//                  }
//              })
//              .orElse(UserDetailsImpl.build(appUser));

      if (appUser.getService().getIsActive()) {
          return UserDetailsImpl.build(appUser);
      } else {
          throw new AccessDeniedException("Service is deactivated");
      }

//      AppUserManagementDto.builder()
//              .service(appUser.getService().getServiceName())
//              .roleId(appUser.getRoles())
//           return UserDetailsImpl.build(appUser);
  }

}
