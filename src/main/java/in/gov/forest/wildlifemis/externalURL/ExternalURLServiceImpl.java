package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ExternalURL;
import in.gov.forest.wildlifemis.domian.NotificationType;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.externalURL.dto.ExternalURL_DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalURLServiceImpl implements ExternalURLServiceInter{
    @Autowired
    ExternalURLRepository externalURLRepository;
    @Override
    public ApiResponse<?> add(ExternalURL_DTO externalURLDto) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            externalURLRepository.save(
                                    ExternalURL.builder()
                                            .url(externalURLDto.getUrl())
                                            .description(externalURLDto.getDescription())
                                            .build()
                            )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to add ExternalURL", new Error("",e.getMessage()));
        }
    }
}
