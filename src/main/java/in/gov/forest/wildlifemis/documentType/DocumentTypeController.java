package in.gov.forest.wildlifemis.documentType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.documentType.dto.DocumentTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
//@RequestMapping("/documentType")
@CrossOrigin("*")
public class DocumentTypeController {

    @Autowired
    DocumentTypeServiceInter documentTypeServiceInter;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/documentType/")
    public ResponseEntity<?> saveNotificationType(@RequestBody DocumentTypeDTO typeOfDocumentDTO){
        ApiResponse<?> apiResponse=documentTypeServiceInter.add(typeOfDocumentDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }


    @GetMapping("/public/documentType/")
    public ResponseEntity<?> getNotificationType(){
        ApiResponse<?> apiResponse=documentTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/documentType/{id}")
    public ResponseEntity<?> updateDocumentType(@PathVariable Long id, @RequestBody DocumentTypeDTO typeOfDocumentDTO) {
        ApiResponse<?> apiResponse=documentTypeServiceInter.update(id, typeOfDocumentDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
