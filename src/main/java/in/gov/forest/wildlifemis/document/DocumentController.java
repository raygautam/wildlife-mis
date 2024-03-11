package in.gov.forest.wildlifemis.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/document")
//@CrossOrigin("*")
public class DocumentController {
    @Autowired
    DocumentServiceInter documentServiceInter;
    @Autowired
    JsonMapper jsonMapper;

//    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> saveDocumentType(
//            @RequestParam(value = "file") MultipartFile file,
//            @RequestParam(value = "documentTypeId") Long documentTypeId,
//            @RequestParam(value = "title") String title
//    ) throws IOException {
//        ApiResponse<?> apiResponse = documentServiceInter.save(file, documentTypeId, title);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
    })
    public ResponseEntity<?> saveNotificationType(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "documentDetails") String documentDetails
    ) {
        DocumentRequestDTO documentRequestDTO = null;
        try{
            if(documentDetails!=null && !documentDetails.isEmpty()){
                documentRequestDTO=jsonMapper.readValue(documentDetails, DocumentRequestDTO.class);
            }else{
                throw new BadRequestException("",new Error("","DocumentDetails is required"));
            }

        }catch (JsonProcessingException e){
            log.info("Error "+e.getMessage());
            throw new JsonProcessingCustomException("",new Error("documentDetails","Invalid documentDetails format "+documentDetails+" recheck and try again!!"));
        }

        if(documentRequestDTO!=null){
            ApiResponse<?> apiResponse = documentServiceInter.save(
                    file , documentRequestDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }

        return ResponseEntity.status(500).body(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllDocument")
    public ResponseEntity<?> getAllDocument() {
        ApiResponse<?> apiResponse = documentServiceInter.getAllDocument();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/getDocument/{documentTypeId}")
    public ResponseEntity<?> getDocument(@PathVariable Long documentTypeId) {
        ApiResponse<?> apiResponse = documentServiceInter.getDocument(documentTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    /**
     * Deletes a document based on the given document ID.
     *
     * @param documentId the ID of the document to be deleted
     * @return an API response indicating the status of the operation
     * on delete operation changing isActive status to false.
     **/
    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<?> delete(@PathVariable Long documentId) {
        ApiResponse<?> apiResponse = documentServiceInter.delete(documentId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        return documentServiceInter.download(id);

    }

}
