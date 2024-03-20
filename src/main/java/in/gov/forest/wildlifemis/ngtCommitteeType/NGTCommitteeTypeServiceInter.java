package in.gov.forest.wildlifemis.ngtCommitteeType;

import in.gov.forest.wildlifemis.ngtCommitteeType.dto.NGTCommitteeTypeDTO;
import in.gov.forest.wildlifemis.common.ApiResponse;

public interface NGTCommitteeTypeServiceInter {
    ApiResponse<?> add(NGTCommitteeTypeDTO ngtCommitteeTypeDTO);

    ApiResponse<?> get();

    ApiResponse<?> update(Long id, NGTCommitteeTypeDTO ngtCommitteeTypeDTO);
}
