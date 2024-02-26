package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.domian.ForestService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForestServiceRepository extends JpaRepository<ForestService, Long> {
}