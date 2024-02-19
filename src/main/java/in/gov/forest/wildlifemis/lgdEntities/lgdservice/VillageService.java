package in.gov.forest.wildlifemis.lgdEntities.lgdservice;


import in.gov.forest.wildlifemis.lgdEntities.entities.Village;
import in.gov.forest.wildlifemis.lgdEntities.repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageService {
    @Autowired
    VillageRepository villageRepository;

    public List<Village> getAllVillages(){
        List<Village> villagesList = villageRepository.findAll();
        return villagesList;


    }

//    public List<Village> getAllVillagesByBlocks(Block blocks){
//       List<Village> villagesList= villageRepository.findAllByBlock(blocks);
//       return villagesList;
//    }
}
