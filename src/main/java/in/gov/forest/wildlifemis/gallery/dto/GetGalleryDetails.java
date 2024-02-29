package in.gov.forest.wildlifemis.gallery.dto;

public interface GetGalleryDetails {
    Long getId();
    String getTitle();
    String getCreatedDate();
    String getGalleryTypeName();
    Boolean getIsActive();
}
