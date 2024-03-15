package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.ExternalURL;
import in.gov.forest.wildlifemis.externalURL.dto.ExternalURL_DTO;

public interface ExternalURLServiceInter {
    ApiResponse<?> add(ExternalURL_DTO externalURLDto);

    ApiResponse<?> get();

    ApiResponse<?> update(ExternalURL_DTO externalURLDto);

//    ExternalURL getExternalURL(Long id);
}
