package in.gov.forest.wildlifemis.ngtCommittee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.ngtCommittee.dto.NGTCommitteeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/ngtCommittee")
@CrossOrigin("*")
public class NGTCommitteeController {
    @Autowired
    NGTCommitteeServiceInter ngtCommitteeServiceInter;
    @Autowired
    JsonMapper jsonMapper;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
    })
    public ResponseEntity<?> save(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "ngtCommitteeDetails") String ngtCommitteeDetails
    ) {
        NGTCommitteeDTO ngtCommitteeDTO = null;
        try{
            if(ngtCommitteeDetails!=null && !ngtCommitteeDetails.isEmpty()){
                ngtCommitteeDTO=jsonMapper.readValue(ngtCommitteeDetails, NGTCommitteeDTO.class);
            }else{
                throw new BadRequestException("",new Error("","ngtCommitteeDetails is required"));
            }
//
        }catch (JsonProcessingException e){
            log.info("Error "+e.getMessage());
            throw new JsonProcessingCustomException("",new Error("ngtCommitteeDetails","Invalid ngtCommitteeDetails format "+ngtCommitteeDetails+" recheck and try again!!"));
        }
//
        if(ngtCommitteeDTO!=null){
            ApiResponse<?> apiResponse = ngtCommitteeServiceInter.save(
                    file , ngtCommitteeDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }

        return ResponseEntity.status(500).body(HttpStatus.INTERNAL_SERVER_ERROR);
//        return ResponseEntity.status(200).body(ngtCommitteeDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNGTCommittee() {
        ApiResponse<?> apiResponse = ngtCommitteeServiceInter.getAllNGTCommittee();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/{ngtCommitteeTypeId}")
    public ResponseEntity<?> getNGTCommittee(@PathVariable Long ngtCommitteeTypeId) {
        ApiResponse<?> apiResponse = ngtCommitteeServiceInter.getNGTCommittee(ngtCommitteeTypeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    /**
     * Deletes a document based on the given document ID.
     *
     * @param ngtCommitteeId the ID of the document to be deleted
     * @return an API response indicating the status of the operation
     * on delete operation changing isActive status to false.
     **/
    @DeleteMapping("/{ngtCommitteeId}")
    public ResponseEntity<?> deleteNGTCommittee(@PathVariable Long ngtCommitteeId) {
        ApiResponse<?> apiResponse = ngtCommitteeServiceInter.deleteNGTCommittee(ngtCommitteeId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
        return ngtCommitteeServiceInter.download(id);

    }

}
