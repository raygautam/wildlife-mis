package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import in.gov.forest.wildlifemis.document.dto.GetDocumentDetails;
import in.gov.forest.wildlifemis.document.dto.GetDocumentDetailsDTO;
import in.gov.forest.wildlifemis.domian.Document;
import in.gov.forest.wildlifemis.domian.DocumentType;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.documentType.DocumentTypeRepository;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentServiceInter{

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentTypeRepository typeOfDocumentRepository;

    @Value("${fileUploadDirectoryForDocument}")
    String fileUploadDirectory;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, DocumentRequestDTO documentRequestDTO) {
//        DocumentType documentType=
        File URL= new File(fileUploadDirectory);
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

        if(documentRequestDTO.getDocumentTypeId()==null){
            throw new BadRequestException("", new Error("documentTypeId","field is required"));
        }

        if( documentRequestDTO.getTitle()==null || Objects.equals(documentRequestDTO.getTitle(), "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if (!URL.exists()) {
            try {
                boolean created = URL.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                }
            } catch (Exception e) {
                throw new IOCustomException("",new Error("","Fail to create directory."+e.getMessage()));
            }
        }


//        String contentType=file.getContentType();
//        String extension = fil.substring(filename.lastIndexOf(".") + 1);
        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis()
                + "."+Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        File destFile = new File(URL + File.separator + randomName);

        try {
            Document document=Document.builder()
                    .title(documentRequestDTO.getTitle())
                    .fileName(randomName)
                    .documentType(
                            typeOfDocumentRepository.findById(documentRequestDTO.getDocumentTypeId())
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
                documentRepository.save(document);
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
    public ApiResponse<?> getDocument(Long documentTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            documentRepository.findByDocumentTypeIdAndIsActiveOrderByCreatedDateDesc(documentTypeId, Boolean.TRUE)
                                    .stream()
                                    .map(
                                            document ->{
                                                return GetDocumentDetailsDTO.builder()
                                                        .id(document.getId())
                                                        .title(document.getTitle())
                                                        .createdDate(new SimpleDateFormat("dd-MM-yyyy").format(document.getCreatedDate()))
                                                        .documentTypeName(document.getDocumentType().getName())
                                                        .isActive(document.getIsActive())
                                                        .build();
                                            }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getAllDocument() {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            documentRepository.findAll(Sort.by("createdDate").descending())
                                    .stream()
                                    .map(document -> {
                                            return GetDocumentDetailsDTO.builder()
                                                    .id(document.getId())
                                                    .title(document.getTitle())
                                                    .createdDate(new SimpleDateFormat("dd-MM-yyyy").format(document.getCreatedDate()))
                                                    .documentTypeName(document.getDocumentType().getName())
                                                    .isActive(document.getIsActive())
                                                    .build();
                                        }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> deleteDocument(Long documentId) {
//        try {
//            return ApiResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .data(
//                            documentRepository.deleteByTypeOfDocumentId(documentId, Boolean.FALSE)
//                    ).build();
//        } catch (DataIntegrityViolationException e) {
//            throw new DatabaseUpdateException ("Fail to delete Data", new Error("","Fail to delete Data"));
//        }

        try{
            return ApiResponse
                    .builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            //Java 8 provides several benefits over updating a field using SQL, including type safety, code re-usability, error handling, and code complexity.
                            documentRepository.findById(documentId)
                                    .stream()
                                    .map(
                                            document -> {
                                                document.setIsActive(Boolean.FALSE);
                                                documentRepository.save(document);
                                                return  "Deleted Successfully";

                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("Document Id  not found", new Error("","Document Id not found")))

                    )
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to delete Document", new Error("",e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> download(Long id) throws IOException {
        Document document = documentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("File not found with id: " + id, new Error("","File not found with id: " + id))
                );
//        String fileName = notification.getFileName();
        // Construct the file path
//        log.info("fileName {}", fileName);

        Path filePath = Paths.get(document.getFileUrl());
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
                        HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + document.getFileName() + "\""
                )
                .contentType(
                        MediaType.parseMediaType(
                        Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()))
                ))
                .body(resource);
    }


}
