package in.gov.forest.wildlifemis.galleryType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.galleryType.dto.GalleryTypeDTO;

public interface GalleryTypeServiceInter {
    ApiResponse<?> add(GalleryTypeDTO galleryTypeDTO);

    ApiResponse<?> get();

    ApiResponse<?> update(Long id, GalleryTypeDTO galleryTypeDTO);
}
