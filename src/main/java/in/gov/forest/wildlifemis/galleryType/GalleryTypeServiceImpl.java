package in.gov.forest.wildlifemis.galleryType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.documentType.dto.DocumentTypeDTO;
import in.gov.forest.wildlifemis.domian.GalleryType;
import in.gov.forest.wildlifemis.exception.DataInsertionException;
import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.NotFoundException;
import in.gov.forest.wildlifemis.galleryType.dto.GalleryTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GalleryTypeServiceImpl implements GalleryTypeServiceInter{

    @Autowired
    GalleryTypeRepository galleryTypeRepository;

    @Override
    public ApiResponse<?> add(GalleryTypeDTO galleryTypeDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(
                            galleryTypeRepository.save(
                                    GalleryType.builder()
                                            .name(galleryTypeDTO.getName())
                                            .build()
                            )
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to save GalleryType", new Error("",e.getMessage()));
        }
    }

    @Override
    public ApiResponse<?> get() {
//        List<NotificationType> sortedList = new ArrayList<>(notificationTypeRepository.findAll());

        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            galleryTypeRepository.findByOrderById()
                    ).build();
        }catch (DataRetrievalException e){
//            Error error=new Error(e.getMessage());
            throw new DataRetrievalException("Failed to retrieve GalleryType", new Error("",e.getMessage()));
        }
    }


    @Override
    public ApiResponse<?> update(Long id, GalleryTypeDTO galleryTypeDTO) {
        try{
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(
                            galleryTypeRepository.findById(id)
                                    .stream()
                                    .map(
                                            notificationType -> {
                                                notificationType.setName(galleryTypeDTO.getName());
                                                galleryTypeRepository.save(notificationType);
                                                return "Updated Successfully";
                                            }
                                    )
                                    .findFirst()
                                    .orElseThrow(()->new NotFoundException("GalleryType not found", new Error("","Not found")))
                    ).build();
        }catch (DataInsertionException e){
            throw new DataInsertionException("Failed to update GalleryType", new Error("",e.getMessage()));
        }
    }
}
