package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.notificationType.NotificationTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter {
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Value("${file.upload.directory}")
    File fileUploadDirectory;

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title) throws IOException {
        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(Collections.singletonList(new Error("file","Please select a file to upload")))
                    .build();
        }

        if( notificationTypeId==null){
            throw new BadRequestException("", new Error("notificationTypeId","field is required"));
        }

        if( title==null || Objects.equals(title, "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if (!fileUploadDirectory.exists()) {
            try {
                boolean created = fileUploadDirectory.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                } else {
                    throw new IOException("Failed to create directory.");
                }
            } catch (IOException e) {
                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }

        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis();
        File destFile = new File(fileUploadDirectory + File.separator + randomName);


        try {
            in.gov.forest.wildlifemis.domian.Notification notification = in.gov.forest.wildlifemis.domian.Notification.builder()
                    .title(title)
                    .fileName(randomName)
                    .notificationType(
                            notificationTypeRepository.findById(notificationTypeId)
                                    .orElseThrow(() -> new DataRetrievalException("error", new Error("","You have provided wrong notificationType Id : " + notificationTypeId)))
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
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                notificationRepository.save(notification)
                        )
                        .build();

            } catch (DataInsertionException e) {
                throw new DataInsertionException("Enable to store notification", new Error("",e.getMessage()));
            }

        } catch (Exception e) {
            // Rollback the file operation if an exception occurs

//This code is also working
//            Path path=Paths.get(destFile.toURI());
//            Files.deleteIfExists(path);
            boolean deleted = false;
            if (destFile.exists() && destFile.isFile()) {
                deleted = destFile.delete(); // Delete the file
            }
            throw e; // Re-throw the exception to propagate it up
        }

    }


    @Override
    public ResponseEntity<?> downloadPDf(Long id) {
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
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @Override
    public ApiResponse<?> getActiveNotification(Long notificationTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository.findByNotificationTypeIdAndIsActive(notificationTypeId, Boolean.TRUE)
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
                                    .findByNotificationTypeIdAndIsArchive(notificationTypeId, Boolean.TRUE)
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
                            notificationRepository.findByIsArchive(Boolean.TRUE)
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update notification", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getAllNotification() {
        notificationRepository.findAll();
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationRepository.findByOrderByCreatedDateDesc()
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
