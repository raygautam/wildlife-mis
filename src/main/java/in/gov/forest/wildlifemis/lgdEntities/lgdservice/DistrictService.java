package in.gov.forest.wildlifemis.lgdEntities.lgdservice;



import in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping.DistrictDTO;
import in.gov.forest.wildlifemis.lgdEntities.entities.District;
import in.gov.forest.wildlifemis.lgdEntities.repository.DistrictRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<DistrictDTO> getAllDistricts(){
        List<District> districts = districtRepository.findAll();
        return  districts.stream().map(districts1 -> modelMapper.map(districts1,DistrictDTO.class)).collect(Collectors.toList());
    }


}
