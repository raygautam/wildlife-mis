package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.externalURL.dto.ExternalURL_DTO;

public interface ExternalURLServiceInter {
    ApiResponse<?> add(ExternalURL_DTO externalURLDto);
}
