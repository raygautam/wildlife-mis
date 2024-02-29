package in.gov.forest.wildlifemis.galleryType;

import in.gov.forest.wildlifemis.domian.GalleryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryTypeRepository extends JpaRepository<GalleryType, Long> {
    Object findByOrderById();
}