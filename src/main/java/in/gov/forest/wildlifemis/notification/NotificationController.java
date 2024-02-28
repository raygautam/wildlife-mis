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
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class NotificationController {

    @Autowired
    NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;


    /**
     * API to add a new notification.
     *
     * @param file the PDF file to be uploaded
     * @param notificationTypeId the id of the notification type
     * @param title the title of the notification
     * @return an ApiResponse object indicating the status of the operation
     * @throws IOException if there is an error in reading or writing the file
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveNotificationType(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "notificationTypeId") Long notificationTypeId,
            @RequestParam(value = "title") String title
    ) throws IOException {
        ApiResponse<?> apiResponse = notificationServiceInter.save(file, notificationTypeId, title);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * API to get all notifications using the specified notificationTypeId and if the isActive is true
     * @PathVariable Long notificationTypeId
     **/
    @GetMapping("/getActiveNotification/{notificationTypeId}")
    public ResponseEntity<?> getActiveNotification(@PathVariable Long notificationTypeId) {
        ApiResponse<?> apiResponse = notificationServiceInter.getActiveNotification(notificationTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    /**
     * API to get all notifications using the specified notificationTypeId and if the isArchive is true
     * **/
    @GetMapping("/getArchiveNotification/{notificationTypeId}")
    public ResponseEntity<?> getArchiveNotification(@PathVariable Long notificationTypeId) {
        ApiResponse<?> apiResponse = notificationServiceInter.getArchiveNotification(notificationTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }


    /**API to download notification resource using Notification Entity id.**/
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
//        ApiResponse<?> apiResponse=notificationServiceInter.downloadPDf(id);
        return notificationServiceInter.downloadPDf(id);

    }

    /**API to archive notification using id of Notification Entity id.**/
    @PutMapping("/archive/{id}")
    public ResponseEntity<?> archive(@PathVariable Long id) {
        ApiResponse<?> apiResponse = notificationServiceInter.archive(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**API to get all archive notification if isArchive is true.**/
    @GetMapping("/getAllArchive")
    public ResponseEntity<?> getAllArchive() {
        ApiResponse<?> apiResponse = notificationServiceInter.getAllArchive();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);


    }

    /**API to get all notification presence notifications table.**/
    @GetMapping("/getAllNotification")
    public ResponseEntity<?> getAllNotification() {
        ApiResponse<?> apiResponse = notificationServiceInter.getAllNotification();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

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

