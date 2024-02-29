package in.gov.forest.wildlifemis.gallery;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Document;
import in.gov.forest.wildlifemis.domian.DocumentType;
import in.gov.forest.wildlifemis.domian.Gallery;
import in.gov.forest.wildlifemis.domian.GalleryType;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.galleryType.GalleryTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
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
public class GalleryServiceImpl implements GalleryServiceInter{
    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    GalleryTypeRepository galleryTypeRepository;
    @Value("${fileUploadDirectory}")
    String fileUploadDirectory;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, Long galleryTypeId, String title) {
        GalleryType galleryType=galleryTypeRepository.findById(galleryTypeId)
                .orElseThrow(
                        ()->new NotFoundException(
                                "Id is not present!",
                                new Error(
                                        "",
                                        "Id is not present!"
                                )
                        )
                );
        File URL= new File(fileUploadDirectory + galleryType.getName());

        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(Collections.singletonList(new Error("file","Please select a file to upload")))
                    .build();
        }

        if(galleryTypeId==null){
            throw new BadRequestException("", new Error("galleryTypeId","field is required"));
        }

        if( title==null || Objects.equals(title, "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if (!URL.exists()) {
            try {
                boolean created = URL.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                } else {
                    throw new IOException("Failed to create directory.");
                }
            } catch (IOException e) {
//                System.out.println("Failed to create directory: " + e.getMessage());
                log.info("Failed to create directory: " + e.getMessage());
            }
        }
//        String contentType=file.getContentType();
//        String extension = fil.substring(filename.lastIndexOf(".") + 1);
        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis()
                + "."+Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        File destFile = new File(URL + File.separator + randomName);

        try {
            Gallery gallery=Gallery.builder()
                    .title(title)
                    .fileName(randomName)
                    .galleryType(galleryType)
                    .fileUrl(String.valueOf(destFile))
                    .createdDate(new Date())
                    .isActive(Boolean.TRUE)
                    .build();

            file.transferTo(destFile);

            try {
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                galleryRepository.save(gallery)
                        )
                        .build();

            } catch (DataInsertionException e) {
                throw new DataInsertionException("Enable to store document", new Error("",e.getMessage()));
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
            throw new RuntimeException(e.getMessage()); // Re-throw the exception to propagate it up
        }
    }

    @Override
    public ApiResponse<?> getGallery(Long galleryTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            galleryRepository.findByGalleryTypeIdAndIsActive(galleryTypeId, Boolean.TRUE)
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> delete(Long galleryId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            galleryRepository.deleteByGalleryTypeId(galleryId, Boolean.FALSE)
                    ).build();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseUpdateException ("Fail to delete Data", new Error("","Fail to delete Data"));
        }
    }

    @Override
    public ResponseEntity<?> download(Long id) {
        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("File not found with id: " + id, new Error("","File not found with id: " + id))
                );
//        String fileName = notification.getFileName();
        // Construct the file path
//        log.info("fileName {}", fileName);

        Path filePath = Paths.get(gallery.getFileUrl());
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
                        HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + gallery.getFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}
