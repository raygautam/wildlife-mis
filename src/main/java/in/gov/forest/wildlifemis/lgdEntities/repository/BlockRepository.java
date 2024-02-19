package in.gov.forest.wildlifemis.lgdEntities.repository;


import in.gov.forest.wildlifemis.lgdEntities.entities.Block;
import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block,Long> {
    List<Block> findAllByDistrict(District district);
}
