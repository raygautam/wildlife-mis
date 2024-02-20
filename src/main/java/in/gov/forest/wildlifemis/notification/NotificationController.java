package in.gov.forest.wildlifemis.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/save")
    public ResponseEntity<?> saveNotificationType(MultipartFile file) throws IOException {
        return ResponseEntity.ok(notificationServiceInter.save(file));
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> saveNotificationType(@PathVariable String fileName){
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+fileName+"\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(notificationServiceInter.download(fileName));
    }
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
