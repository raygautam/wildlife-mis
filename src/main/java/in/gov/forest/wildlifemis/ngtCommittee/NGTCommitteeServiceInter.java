package in.gov.forest.wildlifemis.ngtCommittee;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.document.dto.DocumentRequestDTO;
import in.gov.forest.wildlifemis.ngtCommittee.dto.NGTCommitteeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NGTCommitteeServiceInter {
    ApiResponse<?> save(MultipartFile file, NGTCommitteeDTO ngtCommitteeDTO);

    ResponseEntity<?> download(Long id) throws IOException;

    ApiResponse<?> getAllNGTCommittee();

    ApiResponse<?> getNGTCommittee(Long ngtCommitteeTypeId);

    ApiResponse<?> deleteNGTCommittee(Long ngtCommitteeId);
}
