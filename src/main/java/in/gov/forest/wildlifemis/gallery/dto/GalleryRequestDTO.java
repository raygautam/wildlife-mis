package in.gov.forest.wildlifemis.gallery.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GalleryRequestDTO {
    private Long galleryTypeId;
    private String title;
}
