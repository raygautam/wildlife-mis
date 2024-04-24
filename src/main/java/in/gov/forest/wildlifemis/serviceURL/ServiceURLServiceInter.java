package in.gov.forest.wildlifemis.serviceURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.serviceURL.dto.ServiceURLDTO;

public interface ServiceURLServiceInter {
    ApiResponse<?> add(ServiceURLDTO serviceURLDTO);

    ApiResponse<?> get();
}
