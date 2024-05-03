package in.gov.forest.wildlifemis.documentType;

import in.gov.forest.wildlifemis.domian.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    List<DocumentType> findByOrderById();

    List<DocumentType> findByOrderByName();
}