package in.gov.forest.wildlifemis.documentType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.documentType.dto.DocumentTypeDTO;

public interface DocumentTypeServiceInter {

    ApiResponse<?> add(DocumentTypeDTO typeOfDocumentDTO);

    ApiResponse<?> get();

    ApiResponse<?> update(Long id, DocumentTypeDTO typeOfDocumentDTO);
}
