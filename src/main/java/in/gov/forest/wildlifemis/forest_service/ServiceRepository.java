package in.gov.forest.wildlifemis.forest_service;

import in.gov.forest.wildlifemis.domian.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}