package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.ResourceNotFoundException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Handler;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter{
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Value("${file.upload.directory}")
    private File fileUploadDirectory;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title) throws IOException {
        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(new Error("Please select a file to upload"))
                    .build();
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
        file.transferTo(destFile);

        try {
            Notification notification = Notification.builder()
                    .title(title)
                    .fileName(randomName)
                    .notificationType(notificationTypeRepository.getReferenceById(notificationTypeId))
                    .fileUrl(destFile.getAbsolutePath())
                    .createdDate(new Date())
                    .isActive(Boolean.TRUE)
                    .build();
            // Save notification to the database
            // notificationRepository.save(notification);

            try{
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                notificationRepository.save(notification).getId()
                        )
                        .build();

            }catch (DataInsertionException e){
                throw new DataInsertionException("Enable to store notification",new Error(e.getMessage()));
            }

        } catch (Exception e) {
            // Rollback the file operation if an exception occurs

//This code is also working
//            Path path=Paths.get(destFile.toURI());
//            Files.deleteIfExists(path);
            boolean deleted = false;
            if (destFile.exists() && destFile.isFile()) {
                deleted=destFile.delete(); // Delete the file
            }
            throw e; // Re-throw the exception to propagate it up
        }

    }



    @Override
    public ResponseEntity<?> downloadPDf(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("File not found with id: " + id, new Error("error"))
                );
        String fileName=notification.getFileUrl();
        // Construct the file path
        log.info("fileName {}",fileName);

        Path filePath = Paths.get(String.valueOf(notification));
        log.info("Path {}",filePath);

        // Check if the file exists
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("File not found: " + filePath,new Error("error"));
        }

        // Load the file as a resource
        Resource resource = new FileSystemResource(filePath);

        // Determine the file's content type
//        String contentType = determineContentType(notification.getFileUrl());
        log.info("image : {}",resource);
        // Create the ResponseEntity with the file's content
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + notification.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(String.valueOf(MediaType.APPLICATION_PDF)))
                .body("resource");
//        return ApiResponse.builder()
//                .data(
//                        resource
//                )
//                .build();
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
