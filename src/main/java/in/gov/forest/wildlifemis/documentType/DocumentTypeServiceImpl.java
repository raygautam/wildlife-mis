package in.gov.forest.wildlifemis.documentType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.DocumentType;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import in.gov.forest.wildlifemis.documentType.dto.DocumentTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocumentTypeServiceImpl implements DocumentTypeServiceInter {
    @Autowired
    DocumentTypeRepository typeOfDocumentRepository;
    @Override
    public ApiResponse<?> add(DocumentTypeDTO typeOfDocumentDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            typeOfDocumentRepository.save(
                                    DocumentType.builder()
                                            .name(typeOfDocumentDTO.getName())
                                            .build()
//                                  NotificationTypeMapper.convertDTOToNotificationType(notificationTypeDTO)
                            )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to save documentType", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            typeOfDocumentRepository.findByOrderById()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve documentType", new Error("",e.getMessage()));
        }
    }


    @Override
    public ApiResponse<?> update(Long id, DocumentTypeDTO typeOfDocument) {
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
                                    .orElseThrow(()->new NotFoundException("documentType not found", new Error("","Not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update documentType", new Error("",e.getMessage()));
        }
    }
}
