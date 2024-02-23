package in.gov.forest.wildlifemis.externalURL;

import in.gov.forest.wildlifemis.domian.ExternalURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalURLRepository extends JpaRepository<ExternalURL, Long> {
}