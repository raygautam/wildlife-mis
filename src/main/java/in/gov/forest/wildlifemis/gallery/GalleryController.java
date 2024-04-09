package in.gov.forest.wildlifemis.gallery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.gallery.dto.GalleryRequestDTO;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import in.gov.forest.wildlifemis.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@Slf4j
@RequestMapping("/gallery")
@CrossOrigin("*")
public class GalleryController {

    @Autowired
    GalleryServiceInter galleryServiceInter;
    @Autowired
    JsonMapper jsonMapper;

//    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> saveGallery(
//            @RequestParam(value = "file") MultipartFile file,
//            @RequestParam(value = "galleryTypeId") Long galleryTypeId,
//            @RequestParam(value = "title") String title
//    ) throws IOException {
//        ApiResponse<?> apiResponse = galleryServiceInter.save(file, galleryTypeId, title);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
    })
    public ResponseEntity<?> saveNotificationType(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "galleryDetails") String galleryDetails
    ) {
        GalleryRequestDTO galleryRequestDTO = null;
        try{
            if(galleryDetails!=null && !galleryDetails.isEmpty()){
                galleryRequestDTO=jsonMapper.readValue(galleryDetails, GalleryRequestDTO.class);
            }else{
                throw new BadRequestException("",new Error("","GalleryDetails is required"));
            }

        }catch (JsonProcessingException e){
            log.info("Error "+e.getMessage());
            throw new JsonProcessingCustomException("",new Error("galleryDetails","Invalid galleryDetails format "+galleryDetails+" recheck and try again!!"));
        }

        if(galleryRequestDTO!=null){
            ApiResponse<?> apiResponse = galleryServiceInter.save(
                    file , galleryRequestDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }

        return ResponseEntity.status(500).body(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getGallery/{galleryTypeId}")
    public ResponseEntity<?> getGallery(@PathVariable Long galleryTypeId) {
        ApiResponse<?> apiResponse = galleryServiceInter.getGallery(galleryTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/getAllGallery")
    public ResponseEntity<?> getAllGallery() {
        ApiResponse<?> apiResponse = galleryServiceInter.getAllGallery();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @DeleteMapping("/delete/{galleryId}")
    public ResponseEntity<?> delete(@PathVariable Long galleryId) {
        ApiResponse<?> apiResponse = galleryServiceInter.delete(galleryId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    /**
     If they want to delete permanently the file from the gallery if isActive is false folder and Super Admin.
     **/
//    @DeleteMapping("/deletePermanentlyByGalleryId/{galleryId}")
//    public ResponseEntity<?> deletePermanentlyByGalleryId(@PathVariable Long galleryId) {
//        ApiResponse<?> apiResponse = galleryServiceInter.deletePermanently(galleryId);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//
//    }
//
//    @DeleteMapping("/deletePermanentlyByGalleryTypeId/{galleryTypeId}")
//    public ResponseEntity<?> deletePermanentlyByGalleryTypeId(@PathVariable Long galleryTypeId) {
//        ApiResponse<?> apiResponse = galleryServiceInter.deletePermanentlyByGalleryTypeId(galleryTypeId);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//
//    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
        return galleryServiceInter.download(id);

    }



}
