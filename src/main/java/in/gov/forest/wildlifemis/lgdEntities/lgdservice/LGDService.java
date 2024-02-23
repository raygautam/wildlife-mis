package in.gov.forest.wildlifemis.lgdEntities.lgdservice;



import in.gov.forest.wildlifemis.exception.DataRetrievalException;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.lgdEntities.dtos.responseDTO.*;
import in.gov.forest.wildlifemis.lgdEntities.entities.*;
import in.gov.forest.wildlifemis.lgdEntities.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LGDService {

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
    private DivisionRepository divisionRepository;
    @Autowired
    private RangeRepository rangeRepository;

    @Autowired
    private OperatedBlockRepository operatedBlockRepository;

//    @Autowired
//    ModelMapper modelMapper;

    public LGDService() {}

    public List<StateResponseDTO> getStates() {
        return stateRepository.findAll().stream().map(
                     this::convertToStatesDTO
        ).collect(Collectors.toList());
    }

    public List<DistrictResponseDTO> getDistrictsByStateCode(Long stateCode) {
        return districtRepository.findAllByState(stateRepository.getReferenceById(stateCode))
                .stream().map(
                        this::convertToDistrictDTO
                ).collect(Collectors.toList());
    }


    public List<BlockResponseDTO> getBlocks(Long districtId){
        return blockRepository.findAllByDistrict(districtRepository.getReferenceById(districtId))
                .stream().map(
                        this::convertToBlocksDTO
                ).collect(Collectors.toList());
    }



    public List<VillageResponseDTO> getVillages(Long blockId){
        return villageRepository.findAllByBlock(blockRepository.getReferenceById(blockId))
                .stream().map(
                        this::convertToVillageDTO
                ).collect(Collectors.toList());
    }

    public List<VillageResponseDTO> getVillageTest(Long blockId){
        return villageRepository.findAllBySubDistrictBlock(blockRepository.getReferenceById(blockId))
                .stream().map(
                        this::convertToVillageDTO
                ).collect(Collectors.toList());
    }
    public List<BlockResponseDTO> getAllBlocks(){
        return blockRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Block::getBlockCode))
                .map(
                        this::convertToBlocksDTO
                ).collect(Collectors.toList());
    }

    public List<SubDistrictResponseDTO> getSubAllDistricts() {
        return subDistrictRepository.findAll()
                .stream()
                .map(this::convertToSubDistrictDTO)
                .sorted(Comparator.comparing(SubDistrictResponseDTO::getSubDistrictCode))
                .toList();
    }

    public List<VillageResponseDTO> getAllVillages(){
        return villageRepository.findAll()
                .stream().map(
                        this::convertToVillageDTO
                ).collect(Collectors.toList());
    }
    public SubDistrictResponseDTO convertToSubDistrictDTO(SubDistrict subDistrict){
        return SubDistrictResponseDTO.builder()
                .subDistrictCode(subDistrict.getSubDistrictCode())
                .subDistrictName(subDistrict.getSubDistrictName())
                .district(subDistrict.getDistrict().getDistrictCode())
                .block(subDistrict.getBlock().getBlockCode())
                .build();
    }
    public VillageResponseDTO convertToVillageDTO(Village villages){
        return VillageResponseDTO.builder()
                .villageCode(villages.getVillageCode())
                .villageName(villages.getVillageName())
                .subDistrictCode(villages.getSubDistrict().getSubDistrictCode())
                .subDistrictName(villages.getSubDistrict().getSubDistrictName())
                .build();
    }


    public BlockResponseDTO convertToBlocksDTO(Block block){
        return BlockResponseDTO.builder()
                .blockCode(block.getBlockCode())
                .blockName(block.getBlockName())
                .districtCode(block.getDistrict().getDistrictCode())
                .build();
    }



    public List<NumberOfDistrictDTO> getTotalStates() {
        return districtRepository.
                findTotalNumberOfDistrict();
    }

    public ResponseEntity<List<DistrictResponseDTO>> getDistricts(){
        try{
            return  ResponseEntity.ok(
                    districtRepository.findAll().stream().map(
                            this::convertToDistrictDTO
                    ).sorted(Comparator.comparing(DistrictResponseDTO::getDistrictCode)).toList()
            );
        }catch (DataAccessException e){
            Error error= new Error("",e.getMessage());
            throw new DataRetrievalException("Could not able to retrieved data!",error);
        }

    }

    public ResponseEntity<?> getAllDivisions() {
        try{
            return ResponseEntity.ok(
                    divisionRepository.findAll()
                            .stream()
                            .map(this::convertToDivisionResponseDTO)
                            .sorted(Comparator.comparing(DivisionResponseDTO::getDivisionId))
                            .toList()
            );
        }catch(DataAccessException e){
            Error error= new Error("",e.getMessage());
            throw new DataRetrievalException("Could not able to retrieved data!",error);
        }
    }

    public ResponseEntity<?> getAllRanges() {
        try{
            return ResponseEntity.ok(
                    rangeRepository.findAll()
                            .stream()
                            .map(this::convertToRangeResponseDTO)
                            .sorted(Comparator.comparing(RangeResponseDTO::getRangeId))
                            .toList()
            );
        }catch(DataAccessException e){
            Error error=new Error("",e.getMessage());
            throw new DataRetrievalException("Could not able to retrieved data!",error);
        }
    }

    public ResponseEntity<?> getAllOperationBlocks() {
        try{
            return ResponseEntity.ok(
                    operatedBlockRepository.findAll()
                            .stream()
                            .map(this::convertToOperatedBlockResponseDTO)
                            .sorted(Comparator.comparing(OperatedBlockResponseDTO::getOperatedBlockId))
                            .toList()
            );
        }catch(DataAccessException e){
            Error error=new Error("",e.getMessage());
            throw new DataRetrievalException("Could not able to retrieved data!",error);
        }
    }

    public StateResponseDTO convertToStatesDTO(State state){
        return StateResponseDTO.builder()
                .stateCode(state.getStateCode())
                .stateName(state.getStateName())
                .build();
    }

    public DistrictResponseDTO convertToDistrictDTO(District districts){
        return DistrictResponseDTO.builder()
                .districtCode(districts.getDistrictCode())
                .districtName(districts.getDistrictName())
                .stateCode(districts.getState().getStateCode())
//                .stateName(districts.getState().getStateName())
                .ranges(
                        districts.getRanges().stream().map(
                                range -> {
                                    return RangeResponseDTO.builder()
                                            .rangeId(range.getRangeId())
                                            .rangeName(range.getRangeName())
                                            .build();
                                }
                        ).toList()
                )
                .build();
    }

    public DivisionResponseDTO convertToDivisionResponseDTO(Division division){
        return DivisionResponseDTO.builder()
                .divisionId(division.getId())
                .divisionName(division.getName())
                .serviceId(division.getService().getId())
                .serviceName(division.getService().getServiceName())
                .stateCode(division.getState().getStateCode())
                .stateName(division.getState().getStateName())
                .build();
    }

    public RangeResponseDTO convertToRangeResponseDTO(Range range){
        return RangeResponseDTO.builder()
                .rangeId(range.getRangeId())
                .rangeName(range.getRangeName())
//                .divisionId(range.getDivision().getDivisionId())
//                .divisionName(range.getDivision().getDivisionName())
//                .districtResponseDTOS(range.getDistricts().stream().map(
//                        district -> {
//                            return DistrictResponseDTO.builder()
//                                    .districtCode(district.getDistrictCode())
//                                    .districtName(district.getDistrictName())
//                                    .build();
//                        }
//                ).toList())
                .build();
    }

    public OperatedBlockResponseDTO convertToOperatedBlockResponseDTO(OperatedBlock operatedBlock){
        return OperatedBlockResponseDTO.builder()
                .operatedBlockId(operatedBlock.getOperatedBlockId())
                .operatedBlockName(operatedBlock.getOperatedBlockName())
                .district(
                        DistrictResponseDTO.builder()
                                .districtCode(operatedBlock.getDistrict().getDistrictCode())
                                .districtName(operatedBlock.getDistrict().getDistrictName())
                                .build()
                )
                .range(
                        RangeResponseDTO.builder()
                                .rangeId(operatedBlock.getRange().getRangeId())
                                .rangeName(operatedBlock.getRange().getRangeName())
                                .build()
                )
                .build();
    }
}
