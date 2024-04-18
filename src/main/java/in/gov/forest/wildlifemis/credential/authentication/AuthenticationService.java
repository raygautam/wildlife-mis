package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.common.JwtResponse;
import in.gov.forest.wildlifemis.common.LoginRequestDTO;
import in.gov.forest.wildlifemis.common.TokenRefreshRequest;
import in.gov.forest.wildlifemis.credential.authentication.dto.ChangePasswordDTO;
import in.gov.forest.wildlifemis.credential.jwt.JwtHelper;
import in.gov.forest.wildlifemis.credential.refreshToken.RefreshToken;
import in.gov.forest.wildlifemis.credential.refreshToken.RefreshTokenService;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.exception.AccessDeniedException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private AppUserManagementRepository userRepo;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<?> userLogin(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if(loginRequestDTO.getUserName()==null || loginRequestDTO.getUserName()=="" && loginRequestDTO.getPassword()==null){
//            throw new IllegalArgumentException("username or password cannot be empty");
//        }else{
        log.info("password {}",loginRequestDTO);
//        log.info("email {}", request.getHeader("userName"));

        Authentication authentication = null;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(),
                            loginRequestDTO.getPassword()));
        }catch (BadCredentialsException e){
//            customLoginFailureHandler.onAuthenticationFailure(request,response,e);
            request.setAttribute("userName", loginRequestDTO.getUserName());
            customLoginFailureHandler.onAuthenticationFailure(request,response,e);
            return ApiResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(List.of(new Error("","Access Denied !!")))
                    .data(null)
                    .build();
//            throw new AccessDeniedException("Access Denied !!");
        }


        assert authentication != null;
        if(authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
//                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                UserDetailsImpl userDetails= (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(loginRequestDTO.getUserName());
                String jwt = helper.generateToken(userDetails.getUsername());
                AppUser user = userRepo.findByUserNameAndIsActive(loginRequestDTO.getUserName(), Boolean.TRUE).orElseThrow(null);
                if(!userService.unlockWhenTimeExpired(user)){
                    throw new AccessDeniedException("Account is locked");
                }
               return ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .error(null)
                        .data(
                                JwtResponse.builder()
                                        .token(jwt)
                                        .type("Bearer")
                                        //uncomment one refresh token is implements in front end.
//                                        .refreshToken(refreshTokenService.createRefreshToken(userDetails.getId()).getToken())
                                        .id(userDetails.getId())
                                        .username(userDetails.getUsername())
                                        .serviceId(userDetails.getServiceId())
                                        .rangeId(userDetails.getRangeId())
                                        .divisionId(userDetails.getDivisionId())
                                        .roles(Collections.singletonList(userDetails.getAuthorities().toString()))
                                        .build()
                        ).build();
            }
            else {
//                customLoginFailureHandler.onAuthenticationFailure(request,response);
                return ApiResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(null)
                        .data(null)
                        .build();
            }

    }


    public ApiResponse<?> regenerateToken(TokenRefreshRequest tokenRefreshRequest) {
        try {
            return refreshTokenService.findByToken(tokenRefreshRequest.getRefreshToken())
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getAppUser)
                    .map(userInfo -> {
                        String accessToken = helper.generateToken(userInfo.getUserName());
                        return ApiResponse.builder()
                                .status(HttpStatus.OK.value())
                                .error(null)
                                .data(
                                        JwtResponse.builder()
                                                .token(accessToken)
                                                .type("Bearer")
                                                .refreshToken(tokenRefreshRequest.getRefreshToken())
                                                .build()
                                ).build();
                    }).orElseThrow(() -> new DataRetrievalException(
                            "Refresh token is not in database!",new Error("","provided refresh token is either expired or not present!")));
        }catch (ExpiredJwtException e) {
            Error error=new Error("",e.getMessage());
            return ApiResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(Collections.singletonList(error))
                    .data(null)
                    .build();
        }
    }

        public ApiResponse<?> changePassword(ChangePasswordDTO changePasswordDTO)  {
            try {
                AppUser user = userRepo.findByUserNameAndIsActive(changePasswordDTO.getUserName(), Boolean.TRUE)
                        .orElseThrow(() -> new NotFoundException("", new Error("", "User not found")));

                boolean matches = passwordEncoder.matches(changePasswordDTO.getExistingPassword(), user.getPassword());

                if (!matches) {
                    return ApiResponse.builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .error(Collections.singletonList(new Error("", "Unauthorized")))
                            .build();
                }

                user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepo.save(user);
                return ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data("Password successfully updated. You can now log in with your new password.")
                        .build();
            } catch (Exception ex) {
                return ApiResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(Collections.singletonList(new Error("", "An unexpected error occurred")))
                        .build();
            }

        }
}

//
//
//
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private AppUserRepository userRepo;
//    @Autowired
//    private MessageByLocale messageByLocale;
//
//    @Autowired
//    private RSAKeyPairGenerator rsaKeyPairGenerator;
//
//    @Autowired
//    private AudiTrailRepository audiTrailRepository;
//
//    @Autowired
//    private RequestMeta requestMeta;
//
//    @Autowired
//    private CustomLoginFailureHandler customLoginFailureHandler;
//
//    @Autowired
//    UserService userService;
//
//    public ApiResponse userLogin(LoginRequestDTO loginRequestDTO,
//                                 HttpServletRequest httpServletRequest,
//                                 HttpServletResponse response) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, ServletException, IOException {
//
//
////        log.info("inside login");
////        log.info(Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPrivateKey().getEncoded())) ;
////        log.info("Public key");
////        log.info(Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPublicKey().getEncoded()) );
//        String name = loginRequestDTO.getUserName();
//        String decryptPassword = RSAUtil.decrypt(loginRequestDTO.getPassword(), Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPrivateKey().getEncoded()));
////        log.info("Decrypted Password" + decryptPassword);
//
//        this.authenticate(loginRequestDTO.getUserName(), decryptPassword, httpServletRequest, response);
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequestDTO.getUserName());
//
//        AppUsers user = this.userRepo.findByUserNameAndIsActive(name, Boolean.TRUE);
//        if(!userService.unlockWhenTimeExpired(user)){
//            throw new  AccessDeniedException(messageByLocale.getMessage(Constants.ERROR_MESSAGE.ACCOUNT_LOCKED));
//        }
//
//
//        if (Objects.nonNull(user) && user.getAccountNonLocked()) {
//            if (user.getFailedAttempt() > 0)
//                user.setFailedAttempt(0);
//
//            Set<Roles> roles = new HashSet<>(user.getRoles());
//            List<Roles> rolesList = new ArrayList<>();
//            rolesList.addAll(roles);
//            String token = this.jwtUtil.generateToken(userDetails,user);
//
//            String refreshToken = this.jwtUtil.generateRefreshToken(userDetails);
//
//
//            //Audit trial for login
//            AuditTrail auditTrail = new AuditTrail();
//            if (Objects.isNull(audiTrailRepository.getSlno()))
//                auditTrail.setSlNo(1L);
//            else
//                auditTrail.setSlNo(audiTrailRepository.getSlno() + 1);
//
//            auditTrail.setRemoteAddress(requestMeta.getRemoteAddrs());
//
//            auditTrail.setMethod(requestMeta.getRequestMethod());
//
//            auditTrail.setUrl(requestMeta.getRequestURL());
//
//            auditTrail.setUserName(loginRequestDTO.getUserName());
//            audiTrailRepository.save(auditTrail);
//
//            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
//            loginResponseDTO.setToken(token);
//            loginResponseDTO.setRefreshToken(refreshToken);
//            AppUserDTO appUserDTO = new AppUserDTO();
//
//            loginResponseDTO.setUser(appUserDTO.convertToDTO(user));
//
////        response.setRole(rolesList.stream().map(roles1 -> roles1.getName()).collect(Collectors.toList()));
//            // rolesList.forEach((role)-> response.setRole(role.getRolename().substring(5)));
//
//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setData(loginResponseDTO).setStatus(apiResponse.getStatus());
//            return apiResponse;
//        }
//
//        else
//
//        {
//            throw new AccessDeniedException(messageByLocale.getMessage(Constants.ERROR_MESSAGE.ACCOUNT_LOCKED));
//        }
//
//    }
//
//    private void authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            this.authenticationManager.authenticate(authenticationToken);
//        }catch(BadCredentialsException e) {
//
//            customLoginFailureHandler.onAuthenticationFailure(request,response,e);
//            throw new AccessDeniedException(messageByLocale.getMessage(Constants.ERROR_MESSAGE.ACCESS_DENIED));
//        }
//    }
//
//
//    public ApiResponse regenerateToken() {
//        String jwtToken = requestMeta.getJwtToken();
//        String usernameFromToken = jwtUtil.getUsernameFromToken(jwtToken.substring(7));
//        AppUsers user = this.userRepo.findByUserNameAndIsActive(usernameFromToken,Boolean.TRUE);
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameFromToken);
//        String token = this.jwtUtil.generateToken(userDetails, user);
//        LoginResponseDTO response = new LoginResponseDTO();
//        response.setToken(token);
//
//        AppUserDTO appUserDTO = new AppUserDTO();
//
//        response.setUser( appUserDTO.convertToDTO(user));
//
////        response.setRole(rolesList.stream().map(roles1 -> roles1.getName()).collect(Collectors.toList()));
//        // rolesList.forEach((role)-> response.setRole(role.getRolename().substring(5)));
//
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setData(response).setStatus(apiResponse.getStatus());
//        return apiResponse;
//
//    }
//
