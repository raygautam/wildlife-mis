package in.gov.forest.wildlifemis.gallery;

import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GalleryServiceInter {
    ApiResponse<?> save(MultipartFile file, Long galleryTypeId, String title);

    ApiResponse<?> getGallery(Long galleryTypeId);

    ApiResponse<?> delete(Long galleryId);

    ResponseEntity<?> download(Long id) throws IOException;
}
