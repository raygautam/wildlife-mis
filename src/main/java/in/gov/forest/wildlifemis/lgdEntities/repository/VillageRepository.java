package in.gov.forest.wildlifemis.lgdEntities.repository;



import in.gov.forest.wildlifemis.lgdEntities.entities.Block;
import in.gov.forest.wildlifemis.lgdEntities.entities.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VillageRepository extends JpaRepository<Village,Long> {
    @Query("SELECT v FROM Village v JOIN v.subDistrict s WHERE s.block = ?1")
    List<Village> findAllByBlock(Block block);

    List<Village> findAllBySubDistrictBlock(Block block);

//    @Query("SELECT v FROM Village v JOIN v.subDistrict s WHERE s.block = :block")
//    List<Village> findAllByBlock(@Param("block") Block block);

//    @Modifying
//    @Query("UPDATE Village v SET v.block = :block WHERE v.id = :villageCode")
//    void updateBlockByVillageId(Block block, Long villageCode);
}
