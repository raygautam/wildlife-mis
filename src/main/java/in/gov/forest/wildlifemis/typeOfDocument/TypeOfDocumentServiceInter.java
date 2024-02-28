package in.gov.forest.wildlifemis.typeOfDocument;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
import in.gov.forest.wildlifemis.typeOfDocument.dto.TypeOfDocumentDTO;

public interface TypeOfDocumentServiceInter {

    ApiResponse<?> add(TypeOfDocumentDTO typeOfDocumentDTO);

    ApiResponse<?> get();

    ApiResponse<?> update(Long id, TypeOfDocumentDTO typeOfDocumentDTO);
}
