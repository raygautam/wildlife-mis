package in.gov.forest.wildlifemis.appUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.gov.forest.wildlifemis.appUser.dto.AppUserManagementDto;
import in.gov.forest.wildlifemis.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody @Valid AppUserManagementDto userDetailDto) throws JsonProcessingException {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.insertAppUser(userDetailDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserDetails() {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.get();
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
