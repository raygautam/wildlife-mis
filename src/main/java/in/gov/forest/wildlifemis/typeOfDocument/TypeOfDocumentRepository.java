package in.gov.forest.wildlifemis.typeOfDocument;

import in.gov.forest.wildlifemis.domian.TypeOfDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfDocumentRepository extends JpaRepository<TypeOfDocument, Long> {
    Object findByOrderById();
}