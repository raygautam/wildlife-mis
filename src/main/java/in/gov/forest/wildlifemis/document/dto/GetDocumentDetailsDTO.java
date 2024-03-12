package in.gov.forest.wildlifemis.document.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDocumentDetailsDTO {
    private Long id;
    private String title;
    private String createdDate;
    private String documentTypeName;
    private boolean isActive;
}
