package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.NotificationType;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class NotificationTypeServiceImpl implements NotificationTypeServiceInter{
    @Autowired
     NotificationTypeRepository notificationTypeRepository;
    @Override
    public ApiResponse<?> add(NotificationTypeDTO notificationTypeDTO) {
        try{
            notificationTypeRepository.save(
                    NotificationType.builder()
                            .name(notificationTypeDTO.getName())
                            .build()
            );
          return ApiResponse.builder()
                  .status(HttpStatus.CREATED.value())
                  .data(
                          "Data Inserted Successfully"
                  ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Fail to inserted data", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
//        List<NotificationType> sortedList = new ArrayList<>(notificationTypeRepository.findAll());

        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            notificationTypeRepository.findByOrderByName()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Fail to retrieve", new Error("",e.getMessage()));
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
                                    .map(
                                            notificationType -> {
                                                notificationType.setName(notificationTypeDTO.getName());
                                                notificationTypeRepository.save(notificationType);
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


}
