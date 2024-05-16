package in.gov.forest.wildlifemis.serviceURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.serviceURL.dto.ServiceURLDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(value = "/serviceURL")
@CrossOrigin("*")
public class ServiceURLController {

    @Autowired
    ServiceURLServiceInter serviceURLServiceInter;
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/serviceURL/")
    public ResponseEntity<?> add(@RequestBody ServiceURLDTO serviceURLDTO){
        ApiResponse<?> response = serviceURLServiceInter.add(serviceURLDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/public/serviceURL/")
    public ResponseEntity<?> get(){
        ApiResponse<?> response = serviceURLServiceInter.get();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
