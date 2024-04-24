package in.gov.forest.wildlifemis.serviceURL;

import in.gov.forest.wildlifemis.domian.ServiceURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceURLRepository extends JpaRepository<ServiceURL, Integer> {
}