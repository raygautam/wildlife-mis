package in.gov.forest.wildlifemis.official;

import in.gov.forest.wildlifemis.domian.Official;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficialRepository extends JpaRepository<Official, Long> {
}