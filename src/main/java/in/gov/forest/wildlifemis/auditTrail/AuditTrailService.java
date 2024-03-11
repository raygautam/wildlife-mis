//package in.gov.forest.wildlifemis.auditTrail;
//
//import in.gov.forest.wildlifemis.exception.DataRetrievalException;
//import in.gov.forest.wildlifemis.exception.Error;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuditTrailService {
//    @Autowired
//    private AuditTrailRepository auditTrailRepository;
//
//    public ResponseEntity<?> getAuditTrailData() {
//        try{
//            return ResponseEntity.ok(auditTrailRepository.findAll());
//        }catch (DataAccessException e){
//            throw new DataRetrievalException(
//                    "Failed to fetch audit trail records.",
//                    new Error("","Failed to fetch audit trail records. "+e.getMessage()));
//        }
//    }
//}
