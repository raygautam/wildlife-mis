package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.mapper.NotificationTypeMapper;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationTypeServiceImpl implements NotificationTypeServiceInter{
    @Autowired
     NotificationTypeRepository notificationTypeRepository;
    @Override
    public ApiResponse<?> save(NotificationTypeDTO notificationTypeDTO) {
        try{
          return ApiResponse.builder()
                  .status(HttpStatus.CREATED.value())
                  .data(
                          notificationTypeRepository.save(NotificationTypeMapper.convertDTOToNotificationType(notificationTypeDTO))
                  ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to save notificationType", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                           notificationTypeRepository.findAll().stream()
                                   .map(NotificationTypeMapper::convertNotificationTypeToDTO
                                   ).collect(Collectors.toList())
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve notificationType", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> update(Long id, NotificationTypeDTO notificationTypeDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationTypeRepository.findById(id)
                                    .stream()
                                    .peek(
                                            notificationType -> {
                                                notificationType.setName(notificationTypeDTO.getName());
                                                notificationTypeRepository.save(notificationType);
                                            }
                                    )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update notificationType", new Error("",e.getMessage()));
        }
    }


}
