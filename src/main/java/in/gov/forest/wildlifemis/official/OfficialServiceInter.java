package in.gov.forest.wildlifemis.official;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.official.dto.OfficialDTO;
import org.springframework.web.multipart.MultipartFile;

public interface OfficialServiceInter {
    ApiResponse<?> save(MultipartFile file, OfficialDTO officialDTO);
}
