package in.gov.forest.wildlifemis.typeOfDocument;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.TypeOfDocument;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import in.gov.forest.wildlifemis.typeOfDocument.dto.TypeOfDocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TypeOfDocumentServiceImpl implements TypeOfDocumentServiceInter{
    @Autowired
    TypeOfDocumentRepository typeOfDocumentRepository;
    @Override
    public ApiResponse<?> add(TypeOfDocumentDTO typeOfDocumentDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            typeOfDocumentRepository.save(
                                    TypeOfDocument.builder()
                                            .name(typeOfDocumentDTO.getName())
                                            .build()
//                                  NotificationTypeMapper.convertDTOToNotificationType(notificationTypeDTO)
                            )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to save typeOfDocument", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
//        List<NotificationType> sortedList = new ArrayList<>(notificationTypeRepository.findAll());

        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            typeOfDocumentRepository.findByOrderById()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve typeOfDocument", new Error("",e.getMessage()));
        }
    }


    @Override
    public ApiResponse<?> update(Long id, TypeOfDocumentDTO typeOfDocument) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            typeOfDocumentRepository.findById(id)
                                    .stream()
                                    .map(
                                            notificationType -> {
                                                notificationType.setName(typeOfDocument.getName());
                                                typeOfDocumentRepository.save(notificationType);
                                                return "Updated Successfully";
                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("TypeOfDocument not found", new Error("","NotificationType not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update TypeOfDocument", new Error("",e.getMessage()));
        }
    }
}
