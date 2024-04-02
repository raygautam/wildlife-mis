//package in.gov.forest.wildlifemis.credential.authentication;
//
//import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
//import in.gov.forest.wildlifemis.domian.AppUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Component
//class CustomAuthenticationProvider1 extends AbstractUserDetailsAuthenticationProvider {
////    @Autowired
////    private UserService userService;
//////    @Autowired
//////    private UserDetailsServiceImpl userDetailsService;
////
////    @Override
////    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
////        AppUser user = userService.findByUsername(userDetails.getUsername());
////
////        if (user == null) {
////            throw new BadCredentialsException("Invalid username or password");
////        }
////
////        if (user.isAccountLocked()) {
////            throw new DisabledException("Account is disabled");
////        }
////
////        if (!user.getPassword().equals(userDetails.getPassword())) {
////            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
////            userService.updateUser(user);
////
////            if (user.getFailedLoginAttempts() >= 5) {
////                // Lock the account
////                user.setAccountLocked(true);
////                user.setLockoutTime(LocalDateTime.now().plusHours(10)); // Lockout for 10-12 hours
////                userService.updateUser(user);
////                throw new LockedException("Account is locked. Please try again later.");
////            }
////
////            throw new BadCredentialsException("Invalid username or password");
////        }
////
////        // Reset failed login attempts on successful login
////        user.setFailedLoginAttempts(0);
////        userService.updateUser(user);
////    }
////
////
////    @Override
////    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
////        UserDetails userDetails = (UserDetails) userService.findByUsername(username);
////        if (userDetails == null) {
////            throw new UsernameNotFoundException("User not found");
////        }
////        return userDetails;
////    }
//
//    @Autowired
//    private AppUserManagementRepository userRepository;
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        AppUser user = userRepository
//                .findByUserName(userDetails.getUsername()).orElseThrow(null);
//
//        if (user == null) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        if (user.getAccountLocked()) {
//            throw new DisabledException("Account is disabled");
//        }
//
//        if (!user.getPassword().equals(userDetails.getPassword())) {
//            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
//            userRepository.save(user);
//
//            if (user.getFailedLoginAttempts() >= 5) {
//                // Lock the account
//                user.setAccountLocked(true);
//                user.setLockoutTime(LocalDateTime.now().plusHours(10)); // Lockout for 10-12 hours
//                userRepository.save(user);
//                throw new LockedException("Account is locked. Please try again later.");
//            }
//
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        // Reset failed login attempts on successful login
//        user.setFailedLoginAttempts(0);
//        userRepository.save(user);
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//      AppUser userDetails = userRepository.findByUserName(username).orElseThrow();
//        if (userDetails == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return (UserDetails) userDetails;
//    }
//}
//
