package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    DocumentServiceInter documentServiceInter;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveNotificationType(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "typeOfDocumentId") Long notificationTypeId,
            @RequestParam(value = "title") String title
    ) throws IOException {
        ApiResponse<?> apiResponse = documentServiceInter.save(file, notificationTypeId, title);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getDocument/{typeOfDocumentId}")
    public ResponseEntity<?> getDocument(@PathVariable Long typeOfDocumentId) {
        ApiResponse<?> apiResponse = documentServiceInter.getDocument(typeOfDocumentId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/delete/{typeOfDocumentId}")
    public ResponseEntity<?> delete(@PathVariable Long typeOfDocumentId) {
        ApiResponse<?> apiResponse = documentServiceInter.delete(typeOfDocumentId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        return documentServiceInter.download(id);

    }

}
