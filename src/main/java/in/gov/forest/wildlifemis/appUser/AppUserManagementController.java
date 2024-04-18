package in.gov.forest.wildlifemis.appUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.Error;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/appUser")
public class AppUserManagementController {

    @Autowired
    private AppUserManagementServiceInter appUserManagementServiceInter;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AppUserManagementDto userDetailDto) throws JsonProcessingException {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.insertAppUser(userDetailDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/getUserDetails")
//    public ResponseEntity<?> getUserDetails() {
//        return new ResponseEntity<>(appUserManagementServiceInter.getUserDetails(), HttpStatus.OK);
//    }

//    @GetMapping("/getUserDetailsByLevelName")

    //get userId and roleName
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/getUserIdAndRoleName")
//    public ResponseEntity<?> getUserIdAndRoleName(){
//        return new ResponseEntity<>(userDetailServiceInter.getUserIdAndRoleName(), HttpStatus.OK);
//    }
}
