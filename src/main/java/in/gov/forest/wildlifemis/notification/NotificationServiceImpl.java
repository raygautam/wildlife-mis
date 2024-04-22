package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.GetDocumentDetailsDTO;
import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetailsDTO;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetails;
import in.gov.forest.wildlifemis.notificationType.NotificationTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter {
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Value("${fileUploadDirectoryForNotification}")
    String fileUploadDirectory;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, NotificationRequestDTO addNotificationRequest) {
//        NotificationType notificationType=
        File uploadFileUrl = new File(fileUploadDirectory);

        if(!Objects.equals(file.getContentType(), "application/pdf")){
            return ApiResponse.builder()
                  .status(HttpStatus.BAD_REQUEST.value())
                  .error(Collections.singletonList(new Error("file","File format not supported "+file.getContentType()+"!!")))
                  .build();
        }

        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(Collections.singletonList(new Error("file","Please select a file to upload")))
                    .build();
        }

        if( addNotificationRequest.getNotificationTypeId()==null){
            throw new BadRequestException("", new Error("notificationTypeId","field is required"));
        }

        if(addNotificationRequest.getTitle()==null || Objects.equals(addNotificationRequest.getTitle(), "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if (!uploadFileUrl.exists()) {
            try {
                boolean created = uploadFileUrl.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                }
            } catch (Exception e) {
                throw new IOCustomException("",new Error("","Fail to create directory."+e.getMessage()));
//                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }

        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis()
                + "."+Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        File destFile = new File(uploadFileUrl + File.separator + randomName);


        try {
            in.gov.forest.wildlifemis.domian.Notification notification = in.gov.forest.wildlifemis.domian.Notification.builder()
                    .title(addNotificationRequest.getTitle())
                    .fileName(randomName)
                    .notificationType(
                            notificationTypeRepository
                                    .findById(addNotificationRequest.getNotificationTypeId())
                                    .orElseThrow(
                                            () ->
                                                    new DataRetrievalException(
                                                            "error",
                                                            new Error(
                                                                    "",
                                                                    "You have provided wrong notificationType Id : " + addNotificationRequest.getNotificationTypeId()
                                                            )
                                                    )
                                    )

                    )
//            LocalDateTime.parse(addNotificationRequest.getPublishedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    .fileUrl(String.valueOf(destFile))
                    .createdDate(LocalDateTime.now())
                    .publishedDate(addNotificationRequest.getPublishedDate())
                    .isActive(Boolean.TRUE)
                    .isNew(Boolean.TRUE)
                    .isArchive(Boolean.FALSE)
                    .build();

            file.transferTo(destFile);
            // Save notification to the database
            // notificationRepository.save(notification);

            try {
                Notification notification1=notificationRepository.save(notification);
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                "File inserted successfully."
                        )
                        .build();

            } catch (DataInsertionException e) {
                throw new DataInsertionException("Fail to add notification", new Error("",e.getMessage()));
            }

        } catch (Exception e) {
            // Rollback the file operation if an exception occurs

            //This code is also working
            //Path path=Paths.get(destFile.toURI());
            //Files.deleteIfExists(path);
            boolean deleted = false;
            if (destFile.exists() && destFile.isFile()) {
                deleted = destFile.delete(); // Delete the file
            }
            throw new IOCustomException("",new Error("","Fail to add file to directory."+e.getMessage()));
        }

    }


    @Override
    public ResponseEntity<?> downloadPDf(Long id) throws IOException {
        in.gov.forest.wildlifemis.domian.Notification notification = notificationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("File not found with id: " + id, new Error("","File not found with id: " + id))
                );
//        String fileName = notification.getFileName();
        // Construct the file path
//        log.info("fileName {}", fileName);

        Path filePath = Paths.get(notification.getFileUrl());
//        log.info("Path {}", filePath);

        // Check if the file exists
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("File not found: ", new Error("","File not found: "));
        }

        // Load the file as a resource
        Resource resource = new FileSystemResource(filePath);
//        Resource resource = new UrlResource(filePath.toUri());

        // Determine the file's content type
//        String contentType = determineContentType(notification.getFileUrl());
        log.info("image : {}", resource);
        // Create the ResponseEntity with the file's content
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        //inline for view and attachment for download
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + notification.getFileName() + "\""
                )
                .contentType(
                        MediaType.parseMediaType(
                                Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()))
                        )
                )
                .body(resource);
    }

    @Override
    public ApiResponse<?> getActiveNotification(Long notificationTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository.findByNotificationTypeIdAndIsActiveOrderByCreatedDateDesc(notificationTypeId, Boolean.TRUE)
                                    .stream()
                                    .map(notification->
                                        {
                                            return GetNotificationDetailsDTO.builder()
                                                    .id(notification.getId())
                                                    .title(notification.getTitle())
                                                    .createdDate(notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                                    .notificationTypeName(notification.getNotificationType().getName())
                                                    .isActive(notification.getIsActive())
                                                    .isNew(notification.getIsNew())
                                                    .isArchive(notification.getIsArchive())
                                                    .build();
                                        }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }
    @Override
    public ApiResponse<?> getArchiveNotification(Long notificationTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository
                                    .findByNotificationTypeIdAndIsArchiveOrderByCreatedDateDesc(notificationTypeId, Boolean.TRUE).stream()
                                    .map(notification->
                                        {
                                            return GetNotificationDetailsDTO.builder()
                                                    .id(notification.getId())
                                                    .title(notification.getTitle())
                                                    .createdDate(notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                                    .notificationTypeName(notification.getNotificationType().getName())
                                                    .isActive(notification.getIsActive())
                                                    .isArchive(notification.getIsArchive())
                                                    .isNew(notification.getIsNew())
                                                    .build();
                                        }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getArchiveNotificationByPagination(Long notificationTypeId, Pageable pageable) {
//        Page<Notification> notifications = notificationRepository.findByNotificationTypeId(notificationTypeId, pageable);
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository
                                    .findByNotificationTypeIdAndIsArchiveOrderByCreatedDateDesc(notificationTypeId, Boolean.TRUE, pageable)
                                    .map(notification->
                                            {
                                                return GetNotificationDetailsDTO.builder()
                                                        .id(notification.getId())
                                                        .title(notification.getTitle())
                                                        .createdDate(notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                                        .notificationTypeName(notification.getNotificationType().getName())
                                                        .isActive(notification.getIsActive())
                                                        .isArchive(notification.getIsArchive())
                                                        .isNew(notification.getIsNew())
                                                        .build();
                                            }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> deleteNotification(Long id) {
        try{
            return ApiResponse
                    .builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            //Java 8 provides several benefits over updating a field using SQL, including type safety, code re-usability, error handling, and code complexity.
                            notificationRepository.findById(id)
                                    .stream()
                                    .map(
                                            notification -> {
                                                notification.setIsArchive(Boolean.FALSE);
                                                notification.setIsActive(Boolean.FALSE);
                                                notification.setUpdatedDate(LocalDateTime.now());
                                                notificationRepository.save(notification);
                                                return  "Deleted Successfully";

                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("Notification Id not found", new Error("","Notification Id not found!!")))

                    )
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to delete notification", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> archive(Long id) {
        try{
            return ApiResponse
                    .builder()
                        .status(HttpStatus.OK.value())
                        .data(
                                //Update notification this way help in to way first checking whether id is present or not second if present then just update the required field and save it.
                                //Whereas updating field directly using sql query might give error if id is not present and tracking the error will be difficult.
                                notificationRepository.findById(id)
                                        .stream()
                                        .map(
                                                notification -> {
                                                    notification.setIsArchive(Boolean.TRUE);
                                                    notification.setIsActive(Boolean.FALSE);
                                                    notificationRepository.save(notification);
                                                    return  "Updated Successfully";

                                                }
                                        )
                                        .findFirst()
                                        .orElseThrow(()->new NotFoundException("Notification not found", new Error("","Notification not found")))

                        )
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update notification", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getAllArchive() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository.findByIsArchiveOrderByCreatedDateDesc(Boolean.TRUE)
                                    .stream()
                                    .map(
                                            notification -> {
                                                return GetNotificationDetailsDTO.builder()
                                                        .id(notification.getId())
                                                        .title(notification.getTitle())
                                                        .createdDate(notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                                        .notificationTypeName(notification.getNotificationType().getName())
                                                        .isActive(notification.getIsActive())
                                                        .isArchive(notification.getIsArchive())
                                                        .isNew(notification.getIsNew())
                                                        .build();
                                            }
                                    )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataRetrievalException("Failed to Retrieve notification", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getAllNotification() {

        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository.findByIsActiveOrderByCreatedDateDesc(Boolean.TRUE)
                                    .stream()
                                    .map(
                                             notification -> {
                                                 return GetNotificationDetailsDTO.builder()
                                                         .id(notification.getId())
                                                         .title(notification.getTitle())
                                                         .createdDate(notification.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                                         .notificationTypeName(notification.getNotificationType().getName())
                                                         .isActive(notification.getIsActive())
                                                         .isArchive(notification.getIsArchive())
                                                         .isNew(notification.getIsNew())
                                                         .build();
                                             }
                                    )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update notification", new Error("",e.getMessage()));
        }
    }

    @Scheduled(cron="0 0 2 * * ?")
    @Transactional
    public List<?> deactivateNotification(){
        return notificationRepository.findAll().stream()
                .filter(notification -> notification
                        .getCreatedDate()
                        .plus(Duration.ofDays(15))
                        .isBefore(LocalDateTime.now())
                        && notification.getIsActive()==Boolean.TRUE
                        && notification.getIsNew()==Boolean.TRUE
                )
                .map(
                        notification -> {
                            notification.setIsNew(Boolean.FALSE);
                            notificationRepository.save(notification);
                            return null;
                        }
                ).toList();
    }

}
