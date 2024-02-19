package in.gov.forest.wildlifemis.servicePlus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverageLocationForHACAndARWithRangeRepository extends JpaRepository<CoverageLocationForHACaAndARWithRange, Integer> {
//    Integer findHmcCoverageLocationByRangeRangeId(Integer range);

//    Integer findArCoverageLocationByRangeRangeId(Integer range);

    CoverageLocationForHACaAndARWithRange findByRangeRangeId(Integer range);
}