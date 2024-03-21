package in.gov.forest.wildlifemis.servicePlus.getServicePlusStatus;


import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictLocationMapRepository extends JpaRepository<DistrictLocationMap,Long> {
    DistrictLocationMap findByDistrict(District districtCode);
}
