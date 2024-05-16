package in.gov.forest.wildlifemis.galleryType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.galleryType.dto.GalleryTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/galleryType")
@CrossOrigin("*")
public class GalleryTypeController {

    @Autowired
    GalleryTypeServiceInter galleryTypeServiceInter;
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/public/galleryType/")
    public ResponseEntity<?> saveGalleryType(@RequestBody GalleryTypeDTO galleryTypeDTO){
        ApiResponse<?> apiResponse=galleryTypeServiceInter.add(galleryTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/public/galleryType/")
    public ResponseEntity<?> getGalleryType(){
        ApiResponse<?> apiResponse=galleryTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/galleryType/{id}")
    public ResponseEntity<?> updateGalleryType(@PathVariable Long id, @RequestBody GalleryTypeDTO galleryTypeDTO) {
        ApiResponse<?> apiResponse=galleryTypeServiceInter.update(id, galleryTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

//    @DeleteMapping("/deletePermanently/{id}")
//    public ResponseEntity<?> deleteGalleryType(@PathVariable Long id){
//        ApiResponse<?> apiResponse=galleryTypeServiceInter.deletePermanently(id);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

}
