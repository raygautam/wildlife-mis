package in.gov.forest.wildlifemis.gallery;

import in.gov.forest.wildlifemis.document.dto.GetDocumentDetails;
import in.gov.forest.wildlifemis.domian.Gallery;
import in.gov.forest.wildlifemis.gallery.dto.GetGalleryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<GetGalleryDetails> findByGalleryTypeIdAndIsActive(Long galleryTypeId, Boolean aTrue);

    @Modifying
    @Query("UPDATE Gallery SET isActive = :aFalse WHERE id = :galleryId")
    List<GetGalleryDetails> deleteByGalleryTypeId(Long galleryId, Boolean aFalse);
}