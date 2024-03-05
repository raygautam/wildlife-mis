package in.gov.forest.wildlifemis.gallery;

import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    GalleryServiceInter galleryServiceInter;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveGallery(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "galleryTypeId") Long galleryTypeId,
            @RequestParam(value = "title") String title
    ) throws IOException {
        ApiResponse<?> apiResponse = galleryServiceInter.save(file, galleryTypeId, title);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getGallery/{galleryTypeId}")
    public ResponseEntity<?> getGallery(@PathVariable Long galleryTypeId) {
        ApiResponse<?> apiResponse = galleryServiceInter.getGallery(galleryTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/delete/{galleryId}")
    public ResponseEntity<?> delete(@PathVariable Long galleryId) {
        ApiResponse<?> apiResponse = galleryServiceInter.delete(galleryId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    /**
     If they want to delete permanently the file from the gallery if isActive is false folder and Super Admin.
     **/
    @GetMapping("/deletePermanently/{galleryId}")
    public ResponseEntity<?> deletePermanently(@PathVariable Long galleryId) {
        ApiResponse<?> apiResponse = galleryServiceInter.deletePermanently(galleryId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
        return galleryServiceInter.download(id);

    }
}
