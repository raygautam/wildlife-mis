package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/notification_type")
public class NotificationTypeController {

    @Autowired
    private NotificationTypeServiceInter notificationTypeServiceInter;

    @PostMapping("/save")
    public ResponseEntity<?> saveNotificationType(@RequestBody NotificationTypeDTO notificationTypeDTO){
        ApiResponse<?> apiResponse=notificationTypeServiceInter.save(notificationTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getNotificationType(){
        ApiResponse<?> apiResponse=notificationTypeServiceInter.get();
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
