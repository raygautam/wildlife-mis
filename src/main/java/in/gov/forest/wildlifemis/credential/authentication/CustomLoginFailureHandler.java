package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.exception.AccessDeniedException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.LockedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsServiceImpl appUserService;

    @Autowired
    private AppUserManagementRepository userRepo;

    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

//        String email = "John";

        String email = request.getAttribute("userName").toString();

        UserDetails user =  appUserService.loadUserByUsername(email);
        AppUser appUsers = userRepo.findByUserNameAndIsActive(user.getUsername(), Boolean.TRUE).orElseThrow();

        if (appUsers.getIsActive() && !appUsers.getAccountLocked()) {
            if (appUsers.getFailedLoginAttempts() <= UserService.MAX_FAILED_ATTEMPTS - 1) {
                userService.increaseFailedAttempts(appUsers);
            } else {
                userService.lock(appUsers);
//                exception = new LockedException("Your account has been locked due to 3 failed attempts."
//                        + " It will be unlocked after 24 hours.");
                throw new LockedException(new Error("","Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours."));
            }
        } else if (appUsers.getAccountLocked()) {
            if (userService.unlockWhenTimeExpired(appUsers)) {
//                exception = new LockedException("Your account has been unlocked. Please try to login again.");
                throw new LockedException(new Error("","Your account has been unlocked. Please try to login again."));
            }
        }

    }

}
