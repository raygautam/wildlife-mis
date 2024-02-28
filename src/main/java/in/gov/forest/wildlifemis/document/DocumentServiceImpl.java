//package in.gov.forest.wildlifemis.document;
//
//import in.gov.forest.wildlifemis.common.ApiResponse;
//import in.gov.forest.wildlifemis.exception.BadRequestException;
//import in.gov.forest.wildlifemis.exception.DataInsertionException;
//import in.gov.forest.wildlifemis.exception.DataRetrievalException;
//import in.gov.forest.wildlifemis.exception.Error;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Objects;
//
//@Service
//@Slf4j
//public class DocumentServiceImpl implements DocumentServiceInter{
//
//    @Autowired
//    DocumentRepository documentRepository;
//
//    @Value("${file.upload.directory}")
//    String fileUploadDirectory;
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title) {
//        String acts_Rules_URL=fileUploadDirectory+"actAndRules";
//        if (file.isEmpty()) {
//            return ApiResponse.builder()
//                    .status(HttpStatus.BAD_REQUEST.value())
//                    .error(Collections.singletonList(new Error("file","Please select a file to upload")))
//                    .build();
//        }
//
//        if( notificationTypeId==null){
//            throw new BadRequestException("", new Error("notificationTypeId","field is required"));
//        }
//
//        if( title==null || Objects.equals(title, "")){
//            throw new BadRequestException("", new Error("title","field is required"));
//        }
//
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
//
//
//        try {
//            in.gov.forest.wildlifemis.domian.Notification notification = in.gov.forest.wildlifemis.domian.Notification.builder()
//                    .title(title)
//                    .fileName(randomName)
//                    .notificationType(
//                            notificationTypeRepository.findById(notificationTypeId)
//                                    .orElseThrow(() -> new DataRetrievalException("error", new Error("","You have provided wrong notificationType Id : " + notificationTypeId)))
//                    )
//                    .fileUrl(String.valueOf(destFile))
//                    .createdDate(new Date())
//                    .isActive(Boolean.TRUE)
//                    .isArchive(Boolean.FALSE)
//                    .build();
//
//            file.transferTo(destFile);
//            // Save notification to the database
//            // notificationRepository.save(notification);
//
//            try {
//                return ApiResponse.builder()
//                        .status(HttpStatus.CREATED.value())
//                        .data(
//                                notificationRepository.save(notification)
//                        )
//                        .build();
//
//            } catch (DataInsertionException e) {
//                throw new DataInsertionException("Enable to store notification", new Error("",e.getMessage()));
//            }
//
//        } catch (Exception e) {
//            // Rollback the file operation if an exception occurs
//
////This code is also working
////            Path path=Paths.get(destFile.toURI());
////            Files.deleteIfExists(path);
//            boolean deleted = false;
//            if (destFile.exists() && destFile.isFile()) {
//                deleted = destFile.delete(); // Delete the file
//            }
//            throw e; // Re-throw the exception to propagate it up
//        }
//    }
//
//    @Override
//    public ApiResponse<?> getDocument(Long typeOfDocumentId) {
//        return null;
//    }
//
//    @Override
//    public ApiResponse<?> delete(Long typeOfDocumentId) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<?> download(Long id) {
//        return null;
//    }
//}
