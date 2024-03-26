package in.gov.forest.wildlifemis.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetailsDTO;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/notification")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
public class NotificationController {

    @Autowired
    NotificationServiceInter notificationServiceInter;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JsonMapper jsonMapper;

    @Autowired
    NotificationRepository notificationRepository;






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
//            if(notificationRequestDTO.has("CreatedOn")) {
//                Timestamp stamp = new Timestamp(store.getLong("CreatedOn"));
//                Date date = new Date(stamp.getTime());
//                System.out.println(date);
//            }
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
//    @Operation(summary = "This is to fetch All the Books stored in Db")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Fetched All the Books form Db",
//                    content = {@Content(mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404",
//                    description = "NOt Available",
//                    content = @Content)
//    })
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

    /**
        Delete API do not delete notification permanently instead change the flag of isActive to false.
     **/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id){
        ApiResponse<?> apiResponse = notificationServiceInter.deleteNotification(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

//    @GetMapping("/getAllNotificationByCreatedDateGreaterThanNow")
//    public ResponseEntity<?> getAllNotificationByCreatedDateGreaterThanNow(){
//
//        Duration duration = Duration.ofDays(2);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                notificationRepository.findAll().stream()
//                        .filter(notification -> notification
//                                .getCreatedDate()
//                                .toInstant()
//                                .atZone(ZoneId.systemDefault())
//                                .toLocalDateTime()
//                                .plus(Duration.ofDays(15))
//                                .isBefore(LocalDateTime.now())
//                                && notification.getIsActive()==Boolean.TRUE
//                        )
//                        .map(
//                                notification -> {
//                                    notification.setIsArchive(Boolean.TRUE);
//                                    notification.setIsActive(Boolean.FALSE);
//                                    notificationRepository.save(notification);
//                                    return null;
//                                }
//                        )
//                );
////        ApiResponse<?> apiResponse = notificationServiceInter.deleteNotification(id);
////        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }




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


