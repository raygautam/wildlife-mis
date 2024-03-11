package in.gov.forest.wildlifemis.credential.authentication;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.common.LoginRequestDTO;
import in.gov.forest.wildlifemis.common.TokenRefreshRequest;
import io.github.bucket4j.Bucket;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://10.179.2.158:8080")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
//    private final CaptchaService captchaServ;
//    private final RequestMeta requestMeta;

//    private final MessageByLocale messageByLocale;
//    private final RSAKeyPairGenerator rsaKeyPairGenerator;

    Bucket bucket;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @Autowired
//    public AuthenticationController(AuthenticationService authenticationService, CaptchaService captchaServ, RequestMeta requestMeta, MessageByLocale messageByLocale, RSAKeyPairGenerator rsaKeyPairGenerator) {
//        this.authenticationService = authenticationService;
//
//        this.captchaServ = captchaServ;
//        this.requestMeta = requestMeta;
//        this.messageByLocale = messageByLocale;
//        this.rsaKeyPairGenerator = rsaKeyPairGenerator;
//
//        Bandwidth limit = Bandwidth.classic(120, Refill.greedy(120, Duration.ofMinutes(1)));
//        this.bucket = Bucket.builder()
//                .addLimit(limit)
//                .build();
//    }


//    @GetMapping("/public/getpk")
//    public ResponseEntity<ApiResponse> getCaptcha(HttpServletRequest req ) throws NoSuchAlgorithmException {
//       // req.getHeader("X-Forwarded-For")
//
//        if (bucket.tryConsume(1)) {
//
//            Captcha captcha = captchaServ.createCaptcha(240, 70);
//
//            String captString = captchaServ.encodeCaptcha(captcha);
////            System.out.println("getCapta ip " + req.getRemoteAddr());
//            captchaServ.generateCaptcha(requestMeta.getRemoteAddrs(), captcha.getAnswer());
//            ApiResponse apiResponse = new ApiResponse();
//            CaptchaPublicKeyDTO captchaPublicKeyDTO = new CaptchaPublicKeyDTO();
//            rsaKeyPairGenerator.createKeys();
////            log.info("Captcha", captchaServ.getCaptcha(requestMeta.getRemoteAddrs()));
//
//            captchaPublicKeyDTO.setCaptchaString(captString);
//            captchaPublicKeyDTO.setPublicKey(Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPublicKey().getEncoded()));
//            apiResponse.setData(captchaPublicKeyDTO);
//            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        }
//    }

    @PostMapping("/public/login")
    public ResponseEntity<?> createToken(@RequestBody LoginRequestDTO loginRequestDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws ExecutionException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, ServletException, IOException {

//        System.out.println("Time of token expiration"+new Date(System.currentTimeMillis() + 60 * 1000));
//        if (bucket.tryConsume(1)) {
            log.info("login {}",loginRequestDTO);
            ApiResponse<?> apiResponse = authenticationService.userLogin(loginRequestDTO, request, response);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

//        } else {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        }
    }

    @GetMapping("/public/regenerateToken")
    public ResponseEntity <?> reGenerateToken(@RequestBody TokenRefreshRequest tokenRefreshRequest){
//        if(bucket.tryConsume(1)) {

            ApiResponse<?> apiResponse = authenticationService.regenerateToken(tokenRefreshRequest);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        }
    }

    /**
     * This is the method to change the password by the super admin wihtout requiring to trigeer an email
     *
     * To change the password from the user end first need to take username and password then trigger and email with an otp and then validdate the otp
     * At last ask for the new password along with the old password    *
     * */

//    @PostMapping("/public/changePassword")
//    public ResponseEntity <ApiResponse> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
//        if(bucket.tryConsume(1)) {
//
//            ApiResponse apiResponse = authenticationService.changePassword(changePasswordDTO);
//            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        }
//    }
}

