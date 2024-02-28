package in.gov.forest.wildlifemis.document.dto;

import in.gov.forest.wildlifemis.domian.TypeOfDocument;

public interface GetDocumentDetails {
    Long getId();
    String getTitle();
    String getCreatedDate();
    String getTypeOfDocumentName();
    Boolean getIsActive();

}
