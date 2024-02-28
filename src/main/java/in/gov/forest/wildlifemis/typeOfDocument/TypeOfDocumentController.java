package in.gov.forest.wildlifemis.typeOfDocument;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import in.gov.forest.wildlifemis.typeOfDocument.dto.TypeOfDocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/typeOfDocument")
public class TypeOfDocumentController {

    @Autowired
    TypeOfDocumentServiceInter typeOfDocumentServiceInter;

    @PostMapping("/add")
    public ResponseEntity<?> saveNotificationType(@RequestBody TypeOfDocumentDTO typeOfDocumentDTO){
        ApiResponse<?> apiResponse=typeOfDocumentServiceInter.add(typeOfDocumentDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getNotificationType(){
        ApiResponse<?> apiResponse=typeOfDocumentServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody TypeOfDocumentDTO typeOfDocumentDTO) {
        ApiResponse<?> apiResponse=typeOfDocumentServiceInter.update(id, typeOfDocumentDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
