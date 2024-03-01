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
    public ResponseEntity<?> saveDocumentType(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "documentTypeId") Long documentTypeId,
            @RequestParam(value = "title") String title
    ) throws IOException {
        ApiResponse<?> apiResponse = documentServiceInter.save(file, documentTypeId, title);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getDocument/{documentTypeId}")
    public ResponseEntity<?> getDocument(@PathVariable Long documentTypeId) {
        ApiResponse<?> apiResponse = documentServiceInter.getDocument(documentTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

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