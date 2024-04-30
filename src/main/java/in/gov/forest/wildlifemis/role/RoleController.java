package in.gov.forest.wildlifemis.role;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Role;
import in.gov.forest.wildlifemis.exception.BadRequestException;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/role")
@RestController
@CrossOrigin("*")
public class RoleController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleMRepository;

//    @Autowired
//    private JPAStreamer jpaStreamer;

//    @PostMapping("/insertRole")
//    public ResponseEntity<?> insertRole(@RequestBody Role_mDto roleMDto){
//        roleMDto.setRoleName(roleMDto.getRoleName().toUpperCase());
//        return new ResponseEntity<>(roleMRepository.save(modelMapper.map(roleMDto, Role_m.class)), HttpStatus.CREATED);
//    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> insertRole(@RequestBody List<RoleDto> roleMDto) {

        if (roleMDto.size() > 1) {
            // Batch save using saveAll() method
            try {
                List<Role> roles = roleMDto.stream().map((roleDto) -> modelMapper.map(roleDto, Role.class)).collect(Collectors.toList());
                roleMRepository.saveAll(roles);
                ApiResponse<?> apiResponse=ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data("Data inserted successfully")
                        .build();
                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
            } catch (Exception e) {
                throw new DataInsertionException("Error occurred during data insertion", new Error("",e.getMessage()));
            }
        } else if (roleMDto.size() == 1) {
            // Single save using save() method
            try {
                Role role = modelMapper.map(roleMDto.get(0), Role.class);
                roleMRepository.save(role);
                ApiResponse<?> apiResponse=ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data("Data inserted successfully")
                        .build();
                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
            } catch (Exception e) {
                throw new DataInsertionException("Error occurred during data insertion", new Error("",e.getMessage()));
            }
        } else {
            // Return an error response for empty input
            throw new BadRequestException(
                    "No roles found in the request.",
                    new Error("","No roles found in the request.")
                    );
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getRoles(){
        try {
            List<RoleDto> roleMDto=roleMRepository.findAll()
                    .stream()
                    .filter(role -> role.getName().equalsIgnoreCase("RFO") || role.getName().equalsIgnoreCase("DFO"))
                    .map(role_m -> modelMapper.map(role_m, RoleDto.class))
                    .collect(Collectors.toList());
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(roleMDto)
                    .build();
            return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
        }catch (DataRetrievalException ex){
            throw new DataRetrievalException(
                    "Failed to fetch roles.",
                    new Error("","Failed to fetch roles. "+ex.getMessage()));
        }

    }
}
