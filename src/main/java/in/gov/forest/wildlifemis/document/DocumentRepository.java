package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.document.dto.GetDocumentDetails;
import in.gov.forest.wildlifemis.domian.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<GetDocumentDetails> findByDocumentTypeIdAndIsActiveOrderByCreatedDateDesc(Long typeOfDocumentId, Boolean aTrue);

//    @Modifying
//    @Query("UPDATE Document SET isActive = :aFalse WHERE id = :documentId")
//    List<GetDocumentDetails> deleteByTypeOfDocumentId(Long documentId, Boolean aFalse);
}