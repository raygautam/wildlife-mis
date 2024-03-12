package in.gov.forest.wildlifemis.gallery.dto;

import in.gov.forest.wildlifemis.domian.GalleryType;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class GetGalleryDetailsDTO {
    private Long id;
    private String title;

    private String galleryTypeName;

    private String createdDate;

    private boolean isActive;
}
