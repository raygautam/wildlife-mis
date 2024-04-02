package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsServiceImpl appUserService;

    @Autowired
    private AppUserManagementRepository userRepo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

//        String email = "John";
        String email = request.getHeader("userName");

        UserDetails user =  appUserService.loadUserByUsername(email);
        AppUser appUsers = userRepo.findByUserNameAndIsActive(user.getUsername(), Boolean.TRUE).orElseThrow();

        if (user.isEnabled() && user.isAccountNonLocked()) {
            if (appUsers.getFailedLoginAttempts() <= UserService.MAX_FAILED_ATTEMPTS - 1) {
                userService.increaseFailedAttempts(appUsers);
            } else {
                userService.lock(appUsers);
                exception = new LockedException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours.");
                throw new AccessDeniedException(exception.getMessage());
            }
        } else if (!user.isAccountNonLocked()) {
            if (userService.unlockWhenTimeExpired(appUsers)) {
                exception = new LockedException("Your account has been unlocked. Please try to login again.");
                throw new AccessDeniedException(exception.getMessage());
            }
        }

    }

}
