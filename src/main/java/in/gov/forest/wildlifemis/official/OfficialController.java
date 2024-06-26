package in.gov.forest.wildlifemis.official;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.JsonProcessingCustomException;
import in.gov.forest.wildlifemis.notification.dto.NotificationRequestDTO;
import in.gov.forest.wildlifemis.official.dto.OfficialDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
//@RequestMapping(value = "/official")
@CrossOrigin("*")
public class OfficialController {

    @Autowired
    private OfficialServiceInter officialServiceInter;
    @Autowired
    private JsonMapper jsonMapper;
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping(value = "/official/",  consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
    })
    public ResponseEntity<?> saveNotificationType(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "officialDetails") String officialDetails
    ) {
        OfficialDTO officialDTO = null;
        try{
            if(officialDetails!=null && !officialDetails.isEmpty()){
                officialDTO=jsonMapper.readValue(officialDetails, OfficialDTO.class);
            }else{
                throw new BadRequestException("",new Error("","NotificationDetails is required"));
            }

        }catch (JsonProcessingException e){
            log.info("Error "+e.getMessage());
            throw new JsonProcessingCustomException(officialDetails,new Error("officialDetails","Invalid officialDetails format "+officialDetails+" recheck and try again!!"));
        }

        if(officialDTO!=null){
            ApiResponse<?> apiResponse = officialServiceInter.save(
                    file , officialDTO);
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }

        return ResponseEntity.status(500).body(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/public/official/")
    public ResponseEntity<?> getAllOfficial() {
        ApiResponse<?> apiResponse = officialServiceInter.getAllOfficial();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

//    @PutMapping("/updateOfficialDetails")
//    public ResponseEntity<?> updateOfficialDetails(@RequestBody OfficialDTO officialDTO) {
//        ApiResponse<?> apiResponse=officialServiceInter.save(officialDTO);
//        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//    }


}
