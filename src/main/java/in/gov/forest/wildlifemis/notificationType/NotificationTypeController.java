package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification_type")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
public class NotificationTypeController {

    @Autowired
    private NotificationTypeServiceInter notificationTypeServiceInter;

//    SuperAdmin can enter notification_type
//    @PreAuthorize("hasRole('SuperAdmin')")
    @PostMapping("/")
    public ResponseEntity<?> saveNotificationType(@RequestBody NotificationTypeDTO notificationTypeDTO){
        ApiResponse<?> apiResponse=notificationTypeServiceInter.add(notificationTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getNotificationType(){
        ApiResponse<?> apiResponse=notificationTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody NotificationTypeDTO notificationTypeDTO) {
        ApiResponse<?> apiResponse=notificationTypeServiceInter.update(id, notificationTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getNotificationType(@PathVariable Long id){
//
//    }
//
//    @GetMapping("/delete/{id}")
//    public ResponseEntity<?> getNotificationType(@PathVariable Long id){
//
//    }

}
