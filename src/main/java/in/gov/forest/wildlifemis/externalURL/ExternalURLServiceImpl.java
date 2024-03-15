package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ExternalURL;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.externalURL.dto.ExternalURL_DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ExternalURLServiceImpl implements ExternalURLServiceInter{
    @Autowired
    ExternalURLRepository externalURLRepository;
    @Override
    public ApiResponse<?> add(ExternalURL_DTO externalURLDto) {
        try{
            if(Objects.equals(externalURLDto.getUrl(), "") || externalURLDto.getUrl()==null){
                throw new BadRequestException("", new Error("url","required field!!"));
            }
            if(Objects.equals(externalURLDto.getDescription(), "") || externalURLDto.getDescription()==null){
                throw new BadRequestException("", new Error("description","required field!!"));
            }
            externalURLRepository.save(
                    ExternalURL.builder()
                            .url(externalURLDto.getUrl())
                            .description(externalURLDto.getDescription())
                            .build()
            );
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data("Data inserted Successfully.")
                    .build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to add Data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            externalURLRepository.findAll()
                    ).build();
        }catch (DataRetrievalException e){
            throw new DataRetrievalException("Failed to retrieve external url details ", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> update(ExternalURL_DTO externalURLDto) {
        try{
            if(Objects.equals(externalURLDto.getUrl(), "") || externalURLDto.getUrl()==null){
                throw new BadRequestException("", new Error("url","required field!!"));
            }
            if(Objects.equals(externalURLDto.getDescription(), "") || externalURLDto.getDescription()==null){
                throw new BadRequestException("", new Error("description","required field!!"));
            }

            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                        externalURLRepository.findById(externalURLDto.getId())
                            .stream()
                            .map(
                                    externalURL -> {
                                        externalURL.setUrl(externalURLDto.getUrl());
                                        externalURL.setDescription(externalURLDto.getDescription());
                                        externalURLRepository.save(externalURL);
                                        return "Updated Successfully";
                                    }
                            )
                            .findFirst()
                            .orElseThrow(()->new NotFoundException("ExternalURL not found", new Error("","Not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update external url details ", new Error("",e.getMessage()));
        }
    }
}
