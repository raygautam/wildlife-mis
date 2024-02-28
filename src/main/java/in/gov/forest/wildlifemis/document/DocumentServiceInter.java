package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentServiceInter {
    ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title);

    ApiResponse<?> getDocument(Long typeOfDocumentId);

    ApiResponse<?> delete(Long typeOfDocumentId);

    ResponseEntity<?> download(Long id);
}
