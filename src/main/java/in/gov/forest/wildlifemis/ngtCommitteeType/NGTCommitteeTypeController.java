package in.gov.forest.wildlifemis.ngtCommitteeType;

import in.gov.forest.wildlifemis.ngtCommitteeType.dto.NGTCommitteeTypeDTO;
import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/ngtCommitteeType")
@CrossOrigin("*")
public class NGTCommitteeTypeController {

    @Autowired
    NGTCommitteeTypeServiceInter ngtCommitteeTypeServiceInter;
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/ngtCommitteeType/")
    public ResponseEntity<?> saveGalleryType(@RequestBody NGTCommitteeTypeDTO ngtCommitteeTypeDTO){
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.add(ngtCommitteeTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/public/ngtCommitteeType/")
    public ResponseEntity<?> getGalleryType(){
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PatchMapping("/ngtCommitteeType/{id}")
    public ResponseEntity<?> updateGalleryType(@PathVariable Long id, @RequestBody NGTCommitteeTypeDTO ngtCommitteeTypeDTO) {
        ApiResponse<?> apiResponse=ngtCommitteeTypeServiceInter.update(id, ngtCommitteeTypeDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
