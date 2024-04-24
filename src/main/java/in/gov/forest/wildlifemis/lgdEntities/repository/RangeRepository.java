package in.gov.forest.wildlifemis.lgdEntities.repository;


import in.gov.forest.wildlifemis.lgdEntities.entities.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RangeRepository extends JpaRepository<Range, Integer> {
    List<Range> findByDivisionId(Integer divisionId);
}