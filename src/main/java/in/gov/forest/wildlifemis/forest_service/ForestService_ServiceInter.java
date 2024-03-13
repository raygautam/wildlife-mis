package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.forest_service.dto.ForestServiceRequestDTO;

public interface ForestService_ServiceInter {
    ApiResponse<?> add(ForestServiceRequestDTO serviceRequestDTO);

    ApiResponse<?> get();

    ApiResponse<?> updateProduct(Long id, ForestServiceRequestDTO forestServiceRequestDTO);
}
