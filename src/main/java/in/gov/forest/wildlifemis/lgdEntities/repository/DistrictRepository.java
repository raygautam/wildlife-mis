package in.gov.forest.wildlifemis.lgdEntities.repository;

import in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO.NumberOfDistrictDTO;
import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import in.gov.forest.wildlifemis.lgdEntities.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {


    Collection<District> findAllByState(State referenceById);

//    @Query("SELECT New com.forest.wildlife.lgdEntities.dtos.responseDTO.NumberOfDistrictDTO(COUNT(s.stateCode), d.districtCode, d.districtName) FROM District d JOIN d.state s" +
//            "GROUP BY s.stateCode")
    @Query("SELECT NEW in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO.NumberOfDistrictDTO(COUNT(d.state), d.districtCode, d.districtName) FROM District d JOIN d.state s " +
            "GROUP BY d.state")
    List<NumberOfDistrictDTO> findTotalNumberOfDistrict();
}
