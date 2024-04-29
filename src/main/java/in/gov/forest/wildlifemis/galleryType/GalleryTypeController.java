package in.gov.forest.wildlifemis.galleryType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.galleryType.dto.GalleryTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/galleryType")
@CrossOrigin("*")
public class GalleryTypeController {

    @Autowired
    GalleryTypeServiceInter galleryTypeServiceInter;

    @PostMapping("/")
    public ResponseEntity<?> saveGalleryType(@RequestBody GalleryTypeDTO galleryTypeDTO){
        ApiResponse<?> apiResponse=galleryTypeServiceInter.add(galleryTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getGalleryType(){
        ApiResponse<?> apiResponse=galleryTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping("/{id}")
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
