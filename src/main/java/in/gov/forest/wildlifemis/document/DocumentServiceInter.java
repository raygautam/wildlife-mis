package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentServiceInter {
    ApiResponse<?> save(MultipartFile file, Long documentTypeId, String title) throws IOException;

    ApiResponse<?> getDocument(Long documentTypeId);

    ApiResponse<?> delete(Long documentTypeId);

    ResponseEntity<?> download(Long id);
}
