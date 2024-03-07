package in.gov.forest.wildlifemis.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/notification")
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class NotificationController {

    @Autowired
    NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    JsonMapper jsonMapper;


//    /**
//     * API to add a new notification.
//     *
//     * @param file the PDF file to be uploaded
//     * @param notificationTypeId the id of the notification type
//     * @param title the title of the notification
//     * @return an ApiResponse object indicating the status of the operation
//     * @throws IOException if there is an error in reading or writing the file
//     */
//    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> saveNotificationType(
//            @RequestParam(value = "file") MultipartFile file,
//            @RequestParam(value = "notificationTypeId") Long notificationTypeId,
//            @RequestParam(value = "title") String title
//    ) throws IOException {
//        ApiResponse<?> apiResponse = notificationServiceInter.save(file, notificationTypeId, title);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }


        /**
     * API to add a new notification.
     *
     * @param file the PDF file to be uploaded
     * @param notificationDetails accept the notification details as a JSON of String and then converting to AddNotificationRequest class
     * @return an ApiResponse object indicating the status of the operation
     * @throws IOException if there is an error in reading or writing the file
     */
    @PostMapping(value = "/add",  consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
    })
    public ResponseEntity<?> saveNotificationType(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "notificationDetails") String notificationDetails
    ) {
        NotificationRequestDTO notificationRequestDTO = null;
        try{
            if(notificationDetails!=null && !notificationDetails.isEmpty()){
                notificationRequestDTO=jsonMapper.readValue(notificationDetails, NotificationRequestDTO.class);
            }else{
                throw new BadRequestException("",new Error("","NotificationDetails is required"));
            }

        }catch (JsonProcessingException e){
            log.info("Error "+e.getMessage());
            throw new JsonProcessingCustomException(notificationDetails,new Error("notificationDetails","Invalid notificationDetails format "+notificationDetails+" recheck and try again!!"));
        }

        if(notificationRequestDTO!=null){
            ApiResponse<?> apiResponse = notificationServiceInter.save(
                    file , notificationRequestDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }

        return ResponseEntity.status(500).body(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
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

    @GetMapping("/getArchiveNotificationByPagination/{notificationTypeId}")
    public ResponseEntity<?> getArchiveNotificationByPagination(
            @PathVariable Long notificationTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<?> apiResponse = notificationServiceInter.getArchiveNotificationByPagination(notificationTypeId, pageable);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}


