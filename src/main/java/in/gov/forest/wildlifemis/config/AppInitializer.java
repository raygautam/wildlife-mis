package in.gov.forest.wildlifemis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.forest.wildlifemis.helper.MergeBlockWithSubDistrict;
import in.gov.forest.wildlifemis.lgdEntities.dtos.dtoForLGDMapping.*;
import in.gov.forest.wildlifemis.lgdEntities.entities.*;
import in.gov.forest.wildlifemis.lgdEntities.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class AppInitializer {

    @Value("${AppInitializer.value}")
    private Boolean AppInitializerValue;

    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MergeBlockWithSubDistrict mergeBlockWithSubDistrict;
    @PostConstruct
    private void init() {

        if(AppInitializerValue){

//        Map<Long, SubDistrict> blockAndSubDistrictMapping = new HashMap<>();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>(new Object().toString());

            String LGD_URI = "https://lgdirectory.gov.in/webservices/lgdws/";
            ResponseEntity<List<StateDTO>> stateResponse = restTemplate.exchange(
                    LGD_URI + "stateList",
                    HttpMethod.POST,
                    request,
//                The actual generic type information is lost at runtime.
//                ParameterizedTypeReference helps to overcome this limitation
//                by capturing the type information at compile time and allowing you to retrieve it later.
                    new ParameterizedTypeReference<List<StateDTO>>() {
                    }
            );

            List<StateDTO> stateDTOList = stateResponse.getBody();

            assert stateDTOList != null;
            for (StateDTO s : stateDTOList) {
                State state1 = new State();
                state1.setStateCode(s.getStateCode());
                state1.setStateName(s.getStateNameEnglish());
                stateRepository.save(state1);
                List<SubDistrict> subDistrictList = new ArrayList<>();
                List<Block> blockList = new ArrayList<>();
                List<Village> villagesList = new ArrayList<>();
                if (s.getStateCode() == 17) {
                    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                    formData.add("stateCode", String.valueOf(17));

                    HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(formData, headers);

                    ResponseEntity<List<DistrictDTO>> districtResponse = restTemplate.exchange(
                            LGD_URI + "districtList",
                            HttpMethod.POST,
                            req,
                            new ParameterizedTypeReference<List<DistrictDTO>>() {
                            }
                    );

                    List<DistrictDTO> districtDTOS = districtResponse.getBody();

                    State state = stateRepository.getReferenceById(17L);

                    List<District> districtList = new ArrayList<>();
                    assert districtDTOS != null;
                    for (DistrictDTO d : districtDTOS) {
                        District district = new District();
                        district.setDistrictCode(d.getDistrictCode());
                        district.setDistrictName(d.getDistrictNameEnglish());
                        district.setState(state);
                        districtList.add(district);

                        formData = new LinkedMultiValueMap<>();
                        formData.add("districtCode", String.valueOf(d.getDistrictCode()));
                        req = new HttpEntity<>(formData, headers);

                        ResponseEntity<List<SubDistrictDTO>> subDistrictResponse = restTemplate.exchange(
                                LGD_URI + "subdistrictList",
                                HttpMethod.POST,
                                req,
                                new ParameterizedTypeReference<List<SubDistrictDTO>>() {
                                }
                        );

                        List<SubDistrictDTO> subDistrictDTOS = subDistrictResponse.getBody();

                        ResponseEntity<List<BlocksDTO>> blockResponse = restTemplate.exchange(
                                LGD_URI + "blockList",
                                HttpMethod.POST,
                                req,
                                new ParameterizedTypeReference<List<BlocksDTO>>() {
                                }
                        );

                        List<BlocksDTO> blocksDTOS = blockResponse.getBody();

                        assert subDistrictDTOS != null;
                        Iterator<SubDistrictDTO> subDistrictIterator = subDistrictDTOS.iterator();
                        assert blocksDTOS != null;
                        Iterator<BlocksDTO> blocksIterator = blocksDTOS.iterator();
                        while (subDistrictIterator.hasNext() && blocksIterator.hasNext()) {
                            SubDistrictDTO sd = subDistrictIterator.next();
                            BlocksDTO b = blocksIterator.next();

                            SubDistrict subDistrict = SubDistrict.builder()
                                    .subDistrictCode(sd.getSubdistrictCode())
                                    .subDistrictName(sd.getSubdistrictNameEnglish())
                                    .block(null)
                                    .district(district)
                                    .build();
                            subDistrictList.add(subDistrict);

                            Block block = Block.builder()
                                    .blockCode(b.getBlockCode())
                                    .blockName(b.getBlockNameEnglish())
                                    .district(district)
                                    .build();
                            blockList.add(block);

                            formData = new LinkedMultiValueMap<>();
                            formData.add("subDistrictCode", String.valueOf(sd.getSubdistrictCode()));
                            req = new HttpEntity<>(formData, headers);

                            ResponseEntity<List<VillageDTO>> villageResponse = restTemplate.exchange(
                                    LGD_URI + "villageList",
                                    HttpMethod.POST,
                                    req,
                                    new ParameterizedTypeReference<List<VillageDTO>>() {
                                    }
                            );

                            List<VillageDTO> villageDTOS = villageResponse.getBody();
                            assert villageDTOS != null;
                            for (VillageDTO v : villageDTOS) {
                                Village village = new Village();
                                village.setVillageCode(v.getVillageCode());
                                village.setVillageName(v.getVillageNameEnglish());
                                village.setSubDistrict(subDistrict);
                                villagesList.add(village);
                            }
                        }
                    }
                    districtRepository.saveAllAndFlush(districtList);
                    blockRepository.saveAllAndFlush(blockList);
                    List<SubDistrict> blockAndSubDistrictMapping = mergeBlockWithSubDistrict.mergeListsToMap(blockList, subDistrictList);
                    subDistrictRepository.saveAllAndFlush(blockAndSubDistrictMapping);
                    villageRepository.saveAllAndFlush(villagesList);
                }
            }
        }
    }
}





