package in.gov.forest.wildlifemis.ngtCommitteeType;

import in.gov.forest.wildlifemis.ngtCommitteeType.dto.NGTCommitteeTypeDTO;
import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ngtCommitteeType")
@CrossOrigin("*")
public class NGTCommitteeTypeController {

    @Autowired
    NGTCommitteeTypeServiceInter ngtCommitteeTypeServiceInter;

    @PostMapping("/add")
    public ResponseEntity<?> saveGalleryType(@RequestBody NGTCommitteeTypeDTO ngtCommitteeTypeDTO){
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.add(ngtCommitteeTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getGalleryType(){
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateGalleryType(@PathVariable Long id, @RequestBody NGTCommitteeTypeDTO ngtCommitteeTypeDTO) {
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.update(id, ngtCommitteeTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
