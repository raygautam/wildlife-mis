package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.domian.ForestService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForestServiceRepository extends JpaRepository<ForestService, Long> {
    List<ForestService> findAllByIsActive(Boolean aTrue);
}