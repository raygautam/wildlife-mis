package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.forest_service.dto.ForestServiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forestServices")
//@CrossOrigin(origins = "http://127.0.0.1:5173")
public class ForestServiceController {

    @Autowired
    private ForestService_ServiceInter service_ServiceInter;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ForestServiceRequestDTO serviceRequestDTO){
        ApiResponse<?> apiResponse=service_ServiceInter.add(serviceRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        ApiResponse<?> apiResponse=service_ServiceInter.get();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
