package in.gov.forest.wildlifemis.ngtCommitteeType;

import in.gov.forest.wildlifemis.ngtCommitteeType.dto.NGTCommitteeTypeDTO;
import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.NGTCommitteeType;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NGTCommitteeTypeServiceImpl implements NGTCommitteeTypeServiceInter {

    @Autowired
    NGTCommitteeTypeRepository ngtCommitteeTypeRepository;

    @Override
    public ApiResponse<?> add(NGTCommitteeTypeDTO ngtCommitteeTypeDTO) {
        try{
            ngtCommitteeTypeRepository.save(
                    NGTCommitteeType.builder()
                            .name(ngtCommitteeTypeDTO.name())
                            .build()
            );
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            "Data Inserted successfully"
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to save Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            ngtCommitteeTypeRepository.findByOrderById()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve Data", new Error("",e.getMessage()));
        }
    }


    @Override
    public ApiResponse<?> update(Long id, NGTCommitteeTypeDTO ngtCommitteeTypeDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            ngtCommitteeTypeRepository.findById(id)
                                    .stream()
                                    .map(
                                            ngtCommitteeType -> {
                                                ngtCommitteeType.setName(ngtCommitteeTypeDTO.name());
                                                ngtCommitteeTypeRepository.save(ngtCommitteeType);
                                                return "Updated Successfully";
                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("Not found", new Error("","Not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update Data", new Error("",e.getMessage()));
        }
    }
}
