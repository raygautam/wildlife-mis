package in.gov.forest.wildlifemis.lgdEntities.lgdservice;


import in.gov.forest.wildlifemis.lgdEntities.entities.Block;
import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import in.gov.forest.wildlifemis.lgdEntities.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    public List<Block> getAllBlocks(){
        List<Block> blocksList = blockRepository.findAll();
        return  blocksList;
    }

    public List<Block> geAllBlocksWithDistrict(District district){
        List<Block> blocksList = blockRepository.findAllByDistrict(district);
        return blocksList;
    }
}
