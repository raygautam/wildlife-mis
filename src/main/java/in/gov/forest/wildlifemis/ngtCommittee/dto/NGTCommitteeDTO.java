package in.gov.forest.wildlifemis.ngtCommittee.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record NGTCommitteeDTO(
        @NotBlank Long ngtCommitteeTypeId,
        @NotBlank String title,
        @NotBlank String publishedDate
) {

}
