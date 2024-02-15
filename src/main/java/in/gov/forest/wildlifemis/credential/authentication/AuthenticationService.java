package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import in.gov.forest.wildlifemis.comman.JwtResponse;
import in.gov.forest.wildlifemis.comman.LoginRequestDTO;
import in.gov.forest.wildlifemis.credential.jwt.JwtHelper;
import in.gov.forest.wildlifemis.credential.refreshToken.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtHelper helper;

    public ApiResponse<?> userLogin(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(),
                        loginRequestDTO.getPassword()));
//                        bCryptPasswordEncoder.encode(loginRequest.getPassword())));
//        ApiResponse apiResponse = new ApiResponse();
//        ApiResponse<String> apiResponse= new ApiResponse<>();

        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        try{
//            log.info("userDetails {}", userDetails);
            String jwt = helper.generateToken(userDetails);
//            log.info("jwt {}", jwt);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
//            log.info("roles {}", roles);

//            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//            log.info("roles {}", roles);
//            JwtResponse jwtResponse=JwtResponse.builder()
//                    .token(jwt)
//                    .type("Bearer")
////                                        .refreshToken(refreshTokenService.createRefreshToken(userDetails.getId()).getToken())
//                    .id(userDetails.getId())
//                    .username(userDetails.getUsername())
//                    .roles(roles)
////                            .stateName(userDetails.getStateName())
////                            .divisionName(userDetails.getDivisionName())
////                            .districtName(userDetails.getDistrictName())
////                            .rangeName(userDetails.getRangeName())
////                            .isActive(userDetails.getActive())
//                    .build();
            ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .error(null)
                    .data(
                            JwtResponse.builder()
                                    .token(jwt)
                                    .type("Bearer")
                                    .refreshToken(refreshTokenService.createRefreshToken(userDetails.getId()).getToken())
                                    .id(userDetails.getId())
                                    .username(userDetails.getUsername())
                                    .roles(roles)
//                            .stateName(userDetails.getStateName())
//                            .divisionName(userDetails.getDivisionName())
//                            .districtName(userDetails.getDistrictName())
//                            .rangeName(userDetails.getRangeName())
//                            .isActive(userDetails.getActive())
                                    .build()
                    ).build();

        }else {
            return ApiResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error(null)
                    .data(null)
                    .build();
        }
        return null;
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
//    public ApiResponse changePassword(ChangePasswordDTO changePasswordDTO) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
//
//        AppUsers users = userRepo.findByUserNameAndIsActive(changePasswordDTO.getUserName(), Boolean.TRUE);
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        if(Objects.isNull(users)){
//
//            throw  new ResourceNotFoundException(messageByLocale.getMessage(Constants.ERROR_MESSAGE.RESOURCE_NOT_FOUND));
//        }
////        String decryptPassword = RSAUtil.decrypt(changePasswordDTO.getExistingPassword(), Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPrivateKey().getEncoded()));
////        boolean matches = bCryptPasswordEncoder.matches(decryptPassword, users.getPassword());
//        boolean matches = bCryptPasswordEncoder.matches(changePasswordDTO.getExistingPassword(), users.getPassword());
//
//        if(!matches){
//
//            throw new UnauthourizedException(messageByLocale.getMessage(Constants.ERROR_MESSAGE.UN_AUTHORIZED));
//
//
//        }
//
//        users.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
//        AppUsers save = userRepo.save(users);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setData(save);
//
//        return apiResponse;
//    }