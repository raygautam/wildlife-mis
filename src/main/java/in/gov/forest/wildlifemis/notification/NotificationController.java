package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import jdk.jfr.ContentType;
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
    NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/save")
//    @ContentType(MediaType.MULTIPART_FORM_DATA.APPLICATION_PDF)
    public ResponseEntity<?> saveNotificationType(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("notificationTypeId") Long notificationTypeId,
                                                  @RequestParam("title") String title) throws IOException {
//        return ResponseEntity.ok().body(file.getOriginalFilename()+", "+title+", "+notificationTypeId);
        ApiResponse<?> apiResponse=notificationServiceInter.save(file, notificationTypeId, title);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getActiveNotification/{notificationTypeId}")
    public ResponseEntity<?> getActiveNotification(@PathVariable Long notificationTypeId) {
        ApiResponse<?> apiResponse=notificationServiceInter.getActiveNotification(notificationTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/downloadPDf/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
//        ApiResponse<?> apiResponse=notificationServiceInter.downloadPDf(id);
        return notificationServiceInter.downloadPDf(id);

    }

//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> saveNotificationType(@PathVariable String fileName){
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+fileName+"\"")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(notificationServiceInter.download(fileName));
//    }


//    @GetMapping("/delete/{fileName}")
//    public ResponseEntity<?> getNotificationType(@PathVariable String fileName) throws IOException {
//        ApiResponse<?> apiResponse=notificationServiceInter.delete(fileName);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
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


//    @GetMapping("/path")
//    public ResponseEntity<?> saveNotificationType() throws IOException {
//        URL url;
//        return (ResponseEntity<?>) ResponseEntity.ok(
//                url = resourceLoader.getResource("classpath:").getURL()
//        );
//    }

}
