package in.gov.forest.wildlifemis.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private ResourceLoader resourceLoader;

    //    @PostMapping("/save")
//    public ResponseEntity<?> saveNotificationType(@RequestBody NotificationTypeDTO notificationTypeDTO){
//
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<?> getNotificationType(){
//
//    }
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


    @GetMapping("/path")
    public ResponseEntity<?> saveNotificationType() throws IOException {
        URL url;
        return (ResponseEntity<?>) ResponseEntity.ok(
                url = resourceLoader.getResource("classpath:").getURL()
        );
    }

}
