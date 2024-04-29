package in.gov.forest.wildlifemis.auditTrail;

import in.gov.forest.wildlifemis.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
@CrossOrigin("*")
public class AuditTrailController {
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping("/")
    public ResponseEntity<?> getAuditTrailData() {
        ApiResponse<?> apiResponse = auditTrailService.getAuditTrailData();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//        return auditTrailService.getAuditTrailData();

    }
}
