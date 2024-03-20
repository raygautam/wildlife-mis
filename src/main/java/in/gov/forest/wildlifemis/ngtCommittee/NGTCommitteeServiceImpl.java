package in.gov.forest.wildlifemis.ngtCommittee;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.DocumentRepository;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import in.gov.forest.wildlifemis.document.dto.GetDocumentDetailsDTO;
import in.gov.forest.wildlifemis.documentType.DocumentTypeRepository;
import in.gov.forest.wildlifemis.domian.Document;
import in.gov.forest.wildlifemis.domian.NGTCommittee;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.ngtCommittee.dto.GetNGTCommitteeDTO;
import in.gov.forest.wildlifemis.ngtCommittee.dto.NGTCommitteeDTO;
import in.gov.forest.wildlifemis.ngtCommitteeType.NGTCommitteeTypeRepository;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class NGTCommitteeServiceImpl implements NGTCommitteeServiceInter {

    @Autowired
    NGTCommitteeRepository ngtCommitteeRepository;

    @Autowired
    NGTCommitteeTypeRepository ngtCommitteeTypeRepository;

    @Value("${fileUploadDirectoryForNGTCommittee}")
    String fileUploadDirectory;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, NGTCommitteeDTO ngtCommitteeDTO) {
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

        if(ngtCommitteeDTO.ngtCommitteeTypeId()==null){
            throw new BadRequestException("", new Error("ngtCommitteeTypeId","field is required"));
        }

        if( ngtCommitteeDTO.title()==null || Objects.equals(ngtCommitteeDTO.title(), "")){
            throw new BadRequestException("", new Error("title","field is required"));
        }

        if( ngtCommitteeDTO.publishedDate()==null){
            throw new BadRequestException("", new Error("publishedDate","field is required"));
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
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//            LocalDateTime dateTime = LocalDateTime.parse(ngtCommitteeDTO.publishedDate(), formatter);

            NGTCommittee ngtCommittee=NGTCommittee.builder()
                    .title(ngtCommitteeDTO.title())
                    .fileName(randomName)
                    .ngtCommitteeType(
                            ngtCommitteeTypeRepository.findById(ngtCommitteeDTO.ngtCommitteeTypeId())
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
                    .createdDate(LocalDateTime.now())
                    .publishedDate(new SimpleDateFormat("dd-MM-yyyy").parse(ngtCommitteeDTO.publishedDate()))
                    .isActive(Boolean.TRUE)
                    .build();

            file.transferTo(destFile);

            try {
                ngtCommitteeRepository.save(ngtCommittee);
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
    public ApiResponse<?> getNGTCommittee(Long ngtCommitteeTypeId) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            ngtCommitteeRepository.findByNgtCommitteeTypeIdAndIsActiveOrderByCreatedDateDesc(ngtCommitteeTypeId, Boolean.TRUE)
                                    .stream()
                                    .map(
                                            ngtCommittee ->{
                                                return new GetNGTCommitteeDTO(
                                                        ngtCommittee.getId(),//id
                                                        ngtCommittee.getTitle(),//title
                                                        ngtCommittee.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), //created date,
                                                        ngtCommittee.getNgtCommitteeType().getName(),//ngtCommitteeTypeName
                                                        ngtCommittee.getIsActive(),//isActive
                                                        new SimpleDateFormat("dd-MM-yyyy").format(ngtCommittee.getPublishedDate()) //publishedDate
                                                );
                                            }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> getAllNGTCommittee() {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            ngtCommitteeRepository.findByIsActiveOrderByCreatedDateDesc(Boolean.TRUE)
                                    .stream()
                                    .map(ngtCommittee -> {
                                            return new GetNGTCommitteeDTO(
                                                    ngtCommittee.getId(),//id
                                                    ngtCommittee.getTitle(),//title
                                                    ngtCommittee.getCreatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), //created date,
                                                    ngtCommittee.getNgtCommitteeType().getName(),//ngtCommitteeTypeName
                                                    ngtCommittee.getIsActive(),//isActive
                                                    new SimpleDateFormat("dd-MM-yyyy").format(ngtCommittee.getPublishedDate()) //publishedDate
                                            );
                                        }
                                    )
                    ).build();
        } catch (DataRetrievalException e) {
            throw new DataRetrievalException("Fail to Retrieve Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> deleteNGTCommittee(Long ngtCommitteeId) {

        try{
            return ApiResponse
                    .builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            //Java 8 provides several benefits over updating a field using SQL, including type safety, code re-usability, error handling, and code complexity.
                            ngtCommitteeRepository.findById(ngtCommitteeId)
                                    .stream()
                                    .map(
                                            ngtCommittee -> {
                                                ngtCommittee.setIsActive(Boolean.FALSE);
                                                ngtCommitteeRepository.save(ngtCommittee);
                                                return  "Deleted Successfully";

                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("Not found", new Error("","Not found")))

                    )
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to delete Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> download(Long id) throws IOException {
        NGTCommittee document = ngtCommitteeRepository.findById(id)
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
