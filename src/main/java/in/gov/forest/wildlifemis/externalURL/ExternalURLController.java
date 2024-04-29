package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ExternalURL;
import in.gov.forest.wildlifemis.externalURL.dto.ExternalURL_DTO;
import in.gov.forest.wildlifemis.notification.NotificationServiceInter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5173")
@CrossOrigin("*")
@RequestMapping("/externalURL")
//@Validated
public class ExternalURLController {

    @Autowired
    private ExternalURLServiceInter externalURLServiceInter;

    //Field validation is not used
    @PostMapping("/")
    public ResponseEntity<?> addExternalURL(@RequestBody ExternalURL_DTO externalURLDto) throws IOException {
//        return ResponseEntity.ok("ExternalURLController.");
        ApiResponse<?> apiResponse=externalURLServiceInter.add(externalURLDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getExternalURL() throws IOException {

        ApiResponse<?> apiResponse=externalURLServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateExternalURL( @RequestBody ExternalURL_DTO externalURLDto) throws IOException {
//        return ResponseEntity.ok("ExternalURLController.");
        ApiResponse<?> apiResponse=externalURLServiceInter.update(externalURLDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
//    @GetMapping("/get/{id}")
//    public ExternalURL getExternalURL(@PathVariable Long id) {
//        return externalURLServiceInter.getExternalURL(id);
////        ApiResponse<?> apiResponse=externalURLServiceInter.update(externalURLDto);
////        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }

}
