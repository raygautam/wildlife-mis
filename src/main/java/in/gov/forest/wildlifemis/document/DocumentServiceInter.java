package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentServiceInter {
    ApiResponse<?> save(MultipartFile file, DocumentRequestDTO documentRequestDTO);

    ApiResponse<?> getDocument(Long documentTypeId);

    ApiResponse<?> delete(Long documentTypeId);

    ResponseEntity<?> download(Long id);

    ApiResponse<?> getAllDocument();
}
