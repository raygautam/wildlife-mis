package in.gov.forest.wildlifemis.auditTrail;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetailsDTO;
import in.gov.forest.wildlifemis.util.AESEncryptionUsingSalt;
import in.gov.forest.wildlifemis.util.AESEncryptionWithoutSalt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class AuditTrailService {
    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public ApiResponse<?> getAuditTrailData() {
//        try{
//            return ResponseEntity.ok(auditTrailRepository.findAll());
//        }catch (DataAccessException e){
//            throw new DataRetrievalException(
//                    "Failed to fetch audit trail records.",
//                    new Error("","Failed to fetch audit trail records. "+e.getMessage()));
//        }

        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            auditTrailRepository.findAll()
//                                    .stream()
//                                    .peek(
//                                            auditTrail -> {
//                                                if(auditTrail.getUrl().equals("/public/login")){
//                                                    try {
//                                                        auditTrail.setPayload(AESEncryptionUsingSalt.decrypt(auditTrail.getPayload()));
//                                                    } catch (Exception e) {
//                                                        throw new RuntimeException(e);
//                                                    }
//                                                }
//                                            }
//                                    ).collect(Collectors.toList())
                    ).build();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException("Failed to Retrieve", new Error("",e.getMessage()));
        }

    }
}
