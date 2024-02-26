package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveNotificationType(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "notificationTypeId") Long notificationTypeId,
            @RequestParam(value = "title") String title
    ) throws IOException {
//        return ResponseEntity.ok().body(file.getOriginalFilename()+", "+title+", "+notificationTypeId);

            ApiResponse<?> apiResponse=notificationServiceInter.save(file, notificationTypeId, title);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//        }else{
//                    }
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

    @PutMapping("/archive/{id}")
    public ResponseEntity<?> archive(@PathVariable Long id) {
        //id is notification Entity id.
        ApiResponse<?> apiResponse=notificationServiceInter.archive(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);


    }

    @GetMapping("/getArchive")
    public ResponseEntity<?> getArchive() {
        ApiResponse<?> apiResponse=notificationServiceInter.getArchive();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);


    }
    @GetMapping("/getAllNotification")
    public ResponseEntity<?> getAllNotification() {
        ApiResponse<?> apiResponse=notificationServiceInter.getAllNotification();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);


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
