package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ForestService;
import in.gov.forest.wildlifemis.domian.ServiceURL;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import in.gov.forest.wildlifemis.forest_service.dto.ForestServiceRequestDTO;
import in.gov.forest.wildlifemis.serviceURL.ServiceURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ForestService_ServiceImpl implements ForestService_ServiceInter {
    @Autowired
    private ForestServiceRepository forestServiceRepository;

    @Autowired
    private ServiceURLRepository serviceURLRepository;
    @Override
    public ApiResponse<?> add(ForestServiceRequestDTO serviceRequestDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            forestServiceRepository.save(
                                    ForestService.builder()
                                            .serviceName(serviceRequestDTO.getServiceName())
                                            .serviceId(serviceRequestDTO.getServiceId())
                                            .urlId(serviceURLRepository.findAll().stream().findFirst().get())
                                            .isActive(Boolean.TRUE)
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
            throw new DataRetrievalException("Failed to retrieve", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> update(Long id, ForestServiceRequestDTO forestServiceRequestDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            forestServiceRepository.findById(id)
                                    .stream()
                                    .map(
                                            forestService -> {
                                                forestService.setServiceName(forestServiceRequestDTO.getServiceName());
                                                forestService.setServiceId(forestServiceRequestDTO.getServiceId());
//                                                forestService.setServiceURL(forestServiceRequestDTO.getServiceURL());
                                                forestServiceRepository.save(forestService);
                                                return "Updated Successfully";
                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("Not found", new Error("","Not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            forestServiceRepository.findById(id).stream().map(
                                    forestService -> {
                                        forestService.setIsActive(Boolean.FALSE);
                                        forestServiceRepository.save(forestService);
                                        return "Deleted Successfully";
                                    }
                            )
                    ).build();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException("Failed to delete", new Error("",e.getMessage()));
        }
    }
}
