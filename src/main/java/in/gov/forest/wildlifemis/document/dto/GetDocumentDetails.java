package in.gov.forest.wildlifemis.document.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface GetDocumentDetails {
    Long getId();
    String getTitle();

    String getCreatedDate();
    String getDocumentTypeName();
    Boolean getIsActive();

}
