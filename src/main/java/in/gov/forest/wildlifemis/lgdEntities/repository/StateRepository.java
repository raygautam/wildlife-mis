package in.gov.forest.wildlifemis.lgdEntities.repository;


import in.gov.forest.wildlifemis.lgdEntities.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {
//    @Query("SELECT NEW com.forest.wildlife.lgdEntities.dtos.responseDTO.NumberOfStateDTO(" +
//            "COUNT(s.stateCode), s.stateCode, s.stateName) FROM State s" +
//            "GROUP BY s.stateCode")

//    @Query("SELECT NEW com.forest.wildlife.lgdEntities.dtos.responseDTO.NumberOfStateDTO(" +
//            "COUNT(s.stateCode), s.stateCode, s.stateName) FROM State s " +
//            "GROUP BY s.stateCode")
//    NumberOfDistrictDTO findTotalNumberOfState();
}
