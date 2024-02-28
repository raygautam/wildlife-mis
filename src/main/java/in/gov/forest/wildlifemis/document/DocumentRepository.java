package in.gov.forest.wildlifemis.document;

import in.gov.forest.wildlifemis.domian.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}