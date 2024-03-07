package in.gov.forest.wildlifemis.gallery;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Document;
import in.gov.forest.wildlifemis.domian.DocumentType;
import in.gov.forest.wildlifemis.domian.Gallery;
import in.gov.forest.wildlifemis.domian.GalleryType;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.gallery.dto.GalleryRequestDTO;
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
    @Value("${fileUploadDirectoryForGallery}")
    String fileUploadDirectory;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, GalleryRequestDTO galleryRequestDTO) {
//        GalleryType galleryType=
        File URL= new File(fileUploadDirectory);

        if(!Objects.equals(file.getContentType(), "image/png") &&
                !Objects.equals(file.getContentType(), "image/jpg") &&
                !Objects.equals(file.getContentType(), "image/jpeg")){
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

        if(galleryRequestDTO.getGalleryTypeId()==null){
            throw new BadRequestException("", new Error("galleryTypeId","field is required"));
        }

        if( galleryRequestDTO.getTitle()==null || Objects.equals(galleryRequestDTO.getTitle(), "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if (!URL.exists()) {
            try {
                boolean created = URL.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                }
            } catch (Exception e) {
                log.info("Failed to create directory: " + e.getMessage());
                throw new IOCustomException("",new Error("","Fail to create directory."+e.getMessage()));
            }
        }
//        String contentType=file.getContentType();
//        String extension = fil.substring(filename.lastIndexOf(".") + 1);
        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis()
                + "."+Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        File destFile = new File(URL + File.separator + randomName);

        try {
            Gallery gallery=Gallery.builder()
                    .title(galleryRequestDTO.getTitle())
                    .fileName(randomName)
                    .galleryType(
                            galleryTypeRepository.findById(galleryRequestDTO.getGalleryTypeId())
                                    .orElseThrow(
                                            ()->new NotFoundException(
                                                    "Id is not present!",
                                                    new Error(
                                                            "",
                                                            "Id is not present!"
                                                    )
                                            )
                                    )
                    )
                    .fileUrl(String.valueOf(destFile))
                    .createdDate(new Date())
                    .isActive(Boolean.TRUE)
                    .build();

            file.transferTo(destFile);

            try {
                galleryRepository.save(gallery);
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                "File inserted successfully."
                        )
                        .build();

            } catch (DataInsertionException e) {
                throw new DataInsertionException("Fail to add document", new Error("",e.getMessage()));
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
            throw new IOCustomException("",new Error("","Fail to add file to directory."+e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getGallery(Long galleryTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            galleryRepository.findByGalleryTypeIdAndIsActiveOrderByCreatedDateDesc(galleryTypeId, Boolean.TRUE)
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
                            galleryRepository.deleteByGalleryId(galleryId, Boolean.FALSE)
                    ).build();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseUpdateException ("Fail to delete Data", new Error("","Fail to delete Data"));
        }
    }

    @Override
    public ResponseEntity<?> download(Long id) throws IOException {
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
//        log.info("image : {}", resource);
        // Create the ResponseEntity with the file's content
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        //inline for view and attachment for download
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + gallery.getFileName() + "\""
                )
                .contentType(MediaType.parseMediaType(
                        Files.probeContentType(
                                Path.of(
                                        resource.getFile().getAbsolutePath()
                                )
                        )
                ))
                .body(resource);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> deletePermanently(Long galleryId) {
        ApiResponse<?> response;
        try {
            // Get the file details from the database
            Gallery gallery = galleryRepository.findById(galleryId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("File not found with id: " + galleryId, new Error("","File not found with id: " + galleryId))
                    );
//            Gallery isActive=galleryRepository.findByIsActive(Boolean.FALSE);
            if(!gallery.getIsActive()){
                Path filePath = Paths.get(gallery.getFileUrl());

                // Delete the file from the directory
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
                // Delete the file record from the database
                galleryRepository.delete(gallery);

                response=ApiResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(
                                "File and record deleted successfully."
                        ).build();
            }else {
                response= ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(
                                Collections.singletonList(new Error("","File is active cannot be deleted."))
                        ).build();
            }

//            return "File and record deleted successfully.";
        } catch (IOException e) {
            response= ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error(
                            Collections.singletonList(new  Error("","Error deleting file: " + e.getMessage()))
                    ).build();
//            return "Error deleting file: " + e.getMessage();
        } catch (Exception e) {
            response= ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error(
                            Collections.singletonList(new  Error("","Error deleting file and record: " + e.getMessage()))
                    ).build();
//            return "Error deleting file and record: " + e.getMessage();
        }
        return response;
    }

}
