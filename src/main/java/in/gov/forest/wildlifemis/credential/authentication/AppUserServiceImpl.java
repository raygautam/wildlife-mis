package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserServiceImpl implements UserDetailsService {
  @Autowired
  private AppUserManagementRepository appUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = appUserRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    //taking the roles form the users and then adding them one by one  the roles in the authoritiees
    appUser.getRoles().forEach(roles -> {


      authorities.add(new SimpleGrantedAuthority(roles.getName()));
    });

    return new User(appUser.getUserName(),appUser.getPassword(),authorities);
  }

}
