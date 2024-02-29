package in.gov.forest.wildlifemis.documentType;

import in.gov.forest.wildlifemis.domian.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    Object findByOrderById();
}