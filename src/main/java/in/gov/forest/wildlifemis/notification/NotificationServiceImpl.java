package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter {
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Value("${fileUploadDirectoryForNotification}")
    String fileUploadDirectory;

    @Autowired
    NotificationRepository notificationRepository;

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
                    .fileUrl(String.valueOf(destFile))
                    .createdDate(new Date())
                    .isActive(Boolean.TRUE)
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
                                    .findByNotificationTypeIdAndIsArchiveOrderByCreatedDateDesc(notificationTypeId, Boolean.TRUE)
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
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> archive(Long id) {
        try{
            return ApiResponse
                    .builder()
                        .status(HttpStatus.OK.value())
                        .data(

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
                            notificationRepository.findAll(
                                        PageRequest.of(
                                                0,10,
                                                Sort.by("createdDate")
                                                        .descending()
                                        )
                                    )
                                    .stream()
                                    .map(
                                             notification -> {
                                                 return new GetNotificationDetails() {
                                                     @Override
                                                     public Long getId() {
                                                         return notification.getId();
                                                     }

                                                     @Override
                                                     public String getTitle() {
                                                         return notification.getTitle();
                                                     }

                                                     @Override
                                                     public String getCreatedDate() {
                                                         // You need to get this value from obj
                                                         return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(notification.getCreatedDate());
                                                     }

                                                     @Override
                                                     public String getNotificationTypeName() {
                                                         return notification.getNotificationType().getName();
                                                     }

                                                     @Override
                                                     public Boolean getIsActive() {
                                                         return notification.getIsActive();
                                                     }

                                                     @Override
                                                     public Boolean getIsArchive() {
                                                         return notification.getIsArchive();
                                                     }
                                                 };
                                             }
                                    ).collect(Collectors.toList())
                    ).build();

        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update notification", new Error("",e.getMessage()));
        }
    }


//    @Override
//    public ApiResponse<?> delete(String fileName) throws IOException {
//        File destFile = new File(fileUploadDirectory + File.separator + fileName);
////            Path path=Paths.get(destFile.toURI());
////            Boolean deleted=Files.deleteIfExists(path);
//        boolean deleted = false;
//        if (destFile.exists() && destFile.isFile()) {
//            deleted=destFile.delete(); // Delete the file
//        }
//        return ApiResponse.builder()
//                .status(HttpStatus.OK.value())
//                .data(
//                        deleted
//                )
//                .build();
//    }

        //    @Override
//    public ApiResponse<?> save(MultipartFile file,Long notificationTypeId, String title) throws IOException {
//        if (file.isEmpty()) {
//            return ApiResponse.builder()
//                    .status(HttpStatus.BAD_REQUEST.value())
//                    .error(new Error("Please select a file to upload"))
//                    .build();
//        }
//        if (!fileUploadDirectory.exists()) {
//            try {
//                boolean created = fileUploadDirectory.mkdirs();
//                if (created) {
//                    log.info("Directory created successfully.");
//                } else {
//                    throw new IOException("Failed to create directory.");
//                }
//            } catch (IOException e) {
//                System.out.println("Failed to create directory: " + e.getMessage());
//            }
//        }
//
//        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis();
//        File destFile = new File(fileUploadDirectory + File.separator + randomName);
//        file.transferTo(destFile);
//        Notification.builder()
//                .title(title)
//                .fileName(randomName)
//                .notificationType(notificationTypeRepository.getReferenceById(notificationTypeId))
//                .fileUrl(String.valueOf(destFile))
//                .isActive(Boolean.TRUE)
//                .build();
//        return ApiResponse.builder()
//                .data(
//                        destFile
//                )
//                .build();
//    }

}
