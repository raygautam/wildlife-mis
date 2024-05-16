package in.gov.forest.wildlifemis.appUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.gov.forest.wildlifemis.appUser.dto.AppUserManagementDto;
import in.gov.forest.wildlifemis.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody AppUserManagementDto userDetailDto) throws JsonProcessingException {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.insertAppUser(userDetailDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getUserDetails() {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetailsById(@PathVariable Long id) {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.getById(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }


//    API to unlock the AppUser
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> unlockedUser(@PathVariable Long id) {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.unlockedUser(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        ApiResponse<?> apiResponse=appUserManagementServiceInter.deleteUser(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
