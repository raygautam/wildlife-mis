package in.gov.forest.wildlifemis.serviceURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ServiceURL;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.serviceURL.dto.ServiceURLDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ServiceURLServiceImpl implements ServiceURLServiceInter{

    @Autowired
    ServiceURLRepository serviceURLRepository;
    @Override
    public ApiResponse<?> add(ServiceURLDTO serviceURLDTO) {
        try{
            serviceURLRepository.save(ServiceURL.builder().url(serviceURLDTO.getServiceUrl()).build());
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data("Data Inserted Successfully")
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Fail to add", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(serviceURLRepository.findAll())
                    .build();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException("Fail to retrieve", new Error("",e.getMessage()));
        }
    }
}
