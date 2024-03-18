package in.gov.forest.wildlifemis.lgdEntities.controller;

import in.gov.forest.wildlifemis.lgdEntities.lgdservice.LGDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/lgd")
@CrossOrigin("*")
public class LGDController {

    @Autowired
    LGDService lgdService;

    @GetMapping("/states")
    public ResponseEntity<?> getStates(){
        return ResponseEntity.ok(lgdService.getStates());
    }

    @GetMapping("/districts")
    public ResponseEntity<?> getDistricts(){
        return lgdService.getDistricts();
    }

    @GetMapping("/districts/{stateCode}")
    public ResponseEntity<?> getDistrictsByStateCode(@PathVariable("stateCode") Long stateCode){
        return ResponseEntity.ok(lgdService.getDistrictsByStateCode(stateCode));
    }

    @GetMapping("/blocks")
    public ResponseEntity<?> getAllBlocks(){
        return ResponseEntity.ok(lgdService.getAllBlocks());
    }

    @GetMapping("/blocks/{districtId}")
    public ResponseEntity<?> getBlocks(@PathVariable("districtId") Long districtId){
        return ResponseEntity.ok(lgdService.getBlocks(districtId));
    }

    @GetMapping("/subDistricts")
    public ResponseEntity<?> getSubAllDistricts(){
        return ResponseEntity.ok(lgdService.getSubAllDistricts());
    }

    @GetMapping("/villages")
    public ResponseEntity<?> getAllVillages(){
        return ResponseEntity.ok(lgdService.getAllVillages());
    }

    @GetMapping("/villages/{blockId}")
    public ResponseEntity<?> getVillages(@PathVariable("blockId") Long blockId){
        return ResponseEntity.ok(lgdService.getVillages(blockId));
    }

    @GetMapping("/villageTest/{blockId}")
    public ResponseEntity<?> getVillageTest(@PathVariable("blockId") Long blockId){
        return ResponseEntity.ok(lgdService.getVillageTest(blockId));
    }

    //for testing
    @GetMapping("/totalStates")
    public ResponseEntity<?> getTotalStates(){
        return ResponseEntity.ok(lgdService.getTotalStates());
    }


    @GetMapping("/getAllDivisions")
    public ResponseEntity<?> getAllDivision(){
        return lgdService.getAllDivisions();
    }
    @GetMapping("/getAllRanges")
    public ResponseEntity<?> getAllRanges(){
        return lgdService.getAllRanges();
    }
    @GetMapping("/getAllOperatedBlocks")
    public ResponseEntity<?> getAllOperationBlocks(){
        return lgdService.getAllOperationBlocks();
    }




//    @GetMapping("/hmac")
//    public String generateHmac(@RequestBody District district1) {
//        try {
//            String secretKey="servicePlus123";
//            District district=District.builder()
//                    .districtCode(1522L)
//                    .districtName("East Khasi Hills123")
//                    .build();
//            JsonMapper jsonMapper=new JsonMapper();
//            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            sha256Hmac.init(secretKeySpec);
////            byte[] hmacBytes1 = sha256Hmac.doFinal(district.toString().getBytes(StandardCharsets.UTF_8));
//            byte[] hmacBytes1 = sha256Hmac.doFinal(jsonMapper.writeValueAsString(district).getBytes(StandardCharsets.UTF_8));
//            log.info(jsonMapper.writeValueAsString(district));
//            return Hex.encodeHexString(hmacBytes1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error generating HMAC-SHA256.";
//        }
//
//    }
}
