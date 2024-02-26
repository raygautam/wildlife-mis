package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ForestService;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.forest_service.dto.ForestServiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ForestService_ServiceImpl implements ForestService_ServiceInter {
    @Autowired
    private ForestServiceRepository forestServiceRepository;
    @Override
    public ApiResponse<?> add(ForestServiceRequestDTO serviceRequestDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            forestServiceRepository.save(
                                    ForestService.builder()
                                            .serviceName(serviceRequestDTO.getServiceName())
                                            .build()
                            )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("failed to add service.", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            forestServiceRepository.findAll()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve notificationType", new Error("",e.getMessage()));
        }
    }
}
