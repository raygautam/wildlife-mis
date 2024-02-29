package in.gov.forest.wildlifemis.document.dto;

public interface GetDocumentDetails {
    Long getId();
    String getTitle();
    String getCreatedDate();
    String getDocumentTypeName();
    Boolean getIsActive();

}
