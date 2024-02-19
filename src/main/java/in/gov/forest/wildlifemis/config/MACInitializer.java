package in.gov.forest.wildlifemis.config;


import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.lgdEntities.entities.*;
import in.gov.forest.wildlifemis.lgdEntities.repository.*;
import in.gov.forest.wildlifemis.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Transactional
public class MACInitializer {
    @Value("${MACInitializer.value}")
    private Boolean MACInitializerValue;
    @Autowired
    private  AppUserManagementRepository appUserRepository;
    @Autowired
    private  RoleRepository roleRepository;
//    @Autowired
//    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private  StateRepository stateRepository;
    @Autowired
    private  DivisionRepository divisionRepository;
    @Autowired
    private  DistrictRepository districtRepository;
    @Autowired
    private  RangeRepository rangeRepository;
    @Autowired
    private  OperatedBlockRepository operatedBlockRepository;
    @Autowired
    private  BlockRepository blockRepository;
    @Autowired
    private  VillageRepository villageRepository;



//    private final CitizenRepository citizenRepository;

//    public MACInitializer(UserDetail_tRepository userDetailTRepository, Role_mRepository role_mRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
//                          StateRepository stateRepository, DivisionRepository divisionRepository, DistrictRepository districtRepository, RangeRepository rangeRepository, OperatedBlockRepository operatedBlockRepository, BlockRepository blockRepository, VillageRepository villageRepository, CitizenRepository citizenRepository) {
//        this.userDetailTRepository = userDetailTRepository;
//        this.role_mRepository = role_mRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.stateRepository = stateRepository;
//        this.divisionRepository = divisionRepository;
//        this.districtRepository = districtRepository;
//        this.rangeRepository = rangeRepository;
//        this.operatedBlockRepository = operatedBlockRepository;
//        this.blockRepository = blockRepository;
//        this.villageRepository = villageRepository;
//        this.citizenRepository = citizenRepository;
//    }

    @Transactional
    @PostConstruct
    private void postCreateData() {
        if(MACInitializerValue)
        {
            State state=stateRepository.findById(17L)
                    .orElseThrow(()->new RuntimeException("State with id "+17+" is not present."));
            Set<Division> divisions = Stream.of(
                    Division.builder().id(1).name("Balpakram National Park").service(null).state(state).build(),
                    Division.builder().id(2).name("East & West Garo Hills ").service(null).state(state).build(),
                    Division.builder().id(3).name("Khasi Hills Wildlife Division").service(null).state(state).build(),
                    Division.builder().id(4).name("Jaintia Hills Wildlife Division").service(null).state(state).build()
            ).collect(Collectors.toSet());
            divisionRepository.saveAllAndFlush(divisions);

            Division division1=divisionRepository.findById(1).orElseThrow(()->new RuntimeException("Division with id "+1+" is not present."));
            Division division2=divisionRepository.findById(2).orElseThrow(()->new RuntimeException("Division with id "+2+" is not present."));
            Division division3=divisionRepository.findById(3).orElseThrow(()->new RuntimeException("Division with id "+3+" is not present."));
            Division division4=divisionRepository.findById(4).orElseThrow(()->new RuntimeException("Division with id "+4+" is not present."));

            Set<Range> ranges = Stream.of(
                    Range.builder().rangeId(2360266).rangeName("Siju Range").division(division1).service(null).build(),
                    Range.builder().rangeId(2360264).rangeName("Baghmara Range").division(division1).service(null).build(),
                    Range.builder().rangeId(2360271).rangeName("Mahadeo Range").division(division1).service(null).build(),
                    Range.builder().rangeId(2360470).rangeName("Rongara Research Range").division(division1).service(null).build(),
                    Range.builder().rangeId(2360471).rangeName("Chimitap Beat Independent").division(division1).service(null).build(),

                    Range.builder().rangeId(2360268).rangeName("Southern Nokrek Range").division(division2).service(null).build(),
                    Range.builder().rangeId(2360261).rangeName("East Garo Hills Protection Range ").division(division2).service(null).build(),
                    Range.builder().rangeId(2360262).rangeName("Northern Nokrek Range").division(division2).service(null).build(),
                    Range.builder().rangeId(2360265).rangeName("Park Range").division(division2).service(null).build(),
                    Range.builder().rangeId(2360274).rangeName("Dadenggre Range").division(division2).service(null).build(),
                    Range.builder().rangeId(2360273).rangeName("West Garo Hills Protection Range").division(division2).service(null).build(),

                    Range.builder().rangeId(2360263).rangeName("Nongstoin Range").division(division3).service(null).build(),
                    Range.builder().rangeId(2360270).rangeName("Protection Range Khasi Hills").division(division3).service(null).build(),
                    Range.builder().rangeId(2360260).rangeName("Zoo Range").division(division3).service(null).build(),
                    Range.builder().rangeId(2360275).rangeName("Lum Nehru Park Range").division(division3).service(null).build(),
                    Range.builder().rangeId(2360269).rangeName("Nongpoh Range").division(division3).service(null).build(),

                    Range.builder().rangeId(2360272).rangeName("Jowai Range").division(division4).service(null).build(),
                    Range.builder().rangeId(2360267).rangeName("Umkiang range").division(division4).service(null).build()
            ).collect(Collectors.toSet());
            rangeRepository.saveAllAndFlush(ranges);

            Set<District> districts = Stream.of(
                    District.builder().districtCode(277L).districtName("SOUTH GARO HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360266).rangeName("Siju Range").division(division1).service(null).build(),
                                            Range.builder().rangeId(2360264).rangeName("Baghmara Range").division(division1).service(null).build(),
                                            Range.builder().rangeId(2360271).rangeName("Mahadeo Range").division(division1).service(null).build(),
                                            Range.builder().rangeId(2360470).rangeName("Rongara Research Range").division(division1).service(null).build(),
                                            Range.builder().rangeId(2360471).rangeName("Chimitap Beat Independent").division(division1).service(null).build(),
                                            Range.builder().rangeId(2360268).rangeName("Southern Nokrek Range").division(division2).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(273L).districtName("EAST GARO HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360261).rangeName("East Garo Hills Protection Range ").division(division2).service(null).build(),
                                            Range.builder().rangeId(2360262).rangeName("Northern Nokrek Range").division(division2).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(656L).districtName("NORTH GARO HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360261).rangeName("East Garo Hills Protection Range ").division(division2).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(278L).districtName("WEST GARO HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360262).rangeName("Northern Nokrek Range").division(division2).service(null).build(),
                                            Range.builder().rangeId(2360265).rangeName("Park Range").division(division2).service(null).build(),
                                            Range.builder().rangeId(2360274).rangeName("Dadenggre Range").division(division2).service(null).build(),
                                            Range.builder().rangeId(2360273).rangeName("West Garo Hills Protection Range").division(division2).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(663L).districtName("SOUTH WEST GARO HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360273).rangeName("Protection Range").division(division2).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(658L).districtName("SOUTH WEST KHASI HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360263).rangeName("Nongstoin Range").division(division3).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(279L).districtName("WEST KHASI HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360263).rangeName("Nongstoin Range").division(division3).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(740L).districtName("EASTERN WEST KHASI HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360263).rangeName("Nongstoin Range").division(division3).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(274L).districtName("EAST KHASI HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360270).rangeName("Protection Range Khasi Hills").division(division2).service(null).build(),
                                            Range.builder().rangeId(2360260).rangeName("Zoo Range").division(division3).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(276L).districtName("RI BHOI").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360275).rangeName("Lum Nehru Park Range").division(division3).service(null).build(),
                                            Range.builder().rangeId(2360269).rangeName("Nongpoh Range").division(division3).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(275L).districtName("WEST JAINTIA HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360272).rangeName("Jowai Range").division(division4).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(657L).districtName("EAST JAINTIA HILLS").state(state)
                            .ranges(
                                    Set.of(
                                            Range.builder().rangeId(2360267).rangeName("Umkiang range").division(division4).service(null).build()
                                    )
                            ).build(),
                    District.builder().districtCode(656L).districtName("NORTH GARO HILLS").state(state)
                            .ranges(null).build()
            ).collect(Collectors.toSet());
            districts.forEach(System.out::println);
            districtRepository.saveAllAndFlush(districts);

            District district=districtRepository.getReferenceById(277L);
            District district1=districtRepository.getReferenceById(656L);
            District district2=districtRepository.getReferenceById(273L);
            District district3=districtRepository.getReferenceById(278L);
            District district4=districtRepository.getReferenceById(663L);
            District district5=districtRepository.getReferenceById(279L);
            District district6=districtRepository.getReferenceById(740L);
            District district7=districtRepository.getReferenceById(274L);
            District district8=districtRepository.getReferenceById(276L);
            District district9=districtRepository.getReferenceById(275L);
            District district10=districtRepository.getReferenceById(657L);
            District district11=districtRepository.getReferenceById(658L);

            Range range1=rangeRepository.getReferenceById(2360266);
            Range range2=rangeRepository.getReferenceById(2360264);
            Range range3=rangeRepository.getReferenceById(2360271);
            Range range4=rangeRepository.getReferenceById(2360470);
            Range range5=rangeRepository.getReferenceById(2360471);
            Range range6=rangeRepository.getReferenceById(2360268);
            Range range7=rangeRepository.getReferenceById(2360261);
            Range range8=rangeRepository.getReferenceById(2360262);
            Range range9=rangeRepository.getReferenceById(2360265);
            Range range10=rangeRepository.getReferenceById(2360274);
            Range range11=rangeRepository.getReferenceById(2360273);
            Range range12=rangeRepository.getReferenceById(2360263);
            Range range13=rangeRepository.getReferenceById(2360270);
            Range range14=rangeRepository.getReferenceById(2360260);
            Range range15=rangeRepository.getReferenceById(2360275);
            Range range16=rangeRepository.getReferenceById(2360269);
            Range range17=rangeRepository.getReferenceById(2360272);
            Range range18=rangeRepository.getReferenceById(2360267);


            Set<OperatedBlock> operatedBlocks=Set.copyOf(
                    List.of(
                            OperatedBlock.builder().operatedBlockId(1).operatedBlockName("Siju").district(district).range(range1).build(),
                            OperatedBlock.builder().operatedBlockId(2).operatedBlockName("Baghmara").district(district).range(range2).build(),
                            OperatedBlock.builder().operatedBlockId(3).operatedBlockName("Gasuapara").district(district).range(range2).build(),
                            OperatedBlock.builder().operatedBlockId(4).operatedBlockName("Rongara").district(district).range(range3).build(),
//                OperatedBlock.builder().operatedBlockId(4).operatedBlockName("Rongara - (Mahadeo Range)").district(district).range(range3).build(),
//                OperatedBlock.builder().operatedBlockId(5).operatedBlockName("Rongara - (Rongara Research Range)").district(district).range(range4).build(),
//                OperatedBlock.builder().operatedBlockId(6).operatedBlockName("Rongara - (Chimitap Beat Independent)").district(district).range(range5).build(),
                            OperatedBlock.builder().operatedBlockId(7).operatedBlockName("Chokpot").district(district).range(range6).build(),

                            OperatedBlock.builder().operatedBlockId(8).operatedBlockName("Kharkutta").district(district1).range(range7).build(),
                            OperatedBlock.builder().operatedBlockId(9).operatedBlockName("Resubelpara").district(district1).range(range7).build(),
                            OperatedBlock.builder().operatedBlockId(10).operatedBlockName("Bajengdoba").district(district1).range(range7).build(),

                            OperatedBlock.builder().operatedBlockId(11).operatedBlockName("Dambo Rongjeng").district(district2).range(range7).build(),
                            OperatedBlock.builder().operatedBlockId(12).operatedBlockName("Songsak").district(district2).range(range7).build(),
                            OperatedBlock.builder().operatedBlockId(13).operatedBlockName("Samanda").district(district2).range(range7).build(), OperatedBlock.builder().operatedBlockId(13).operatedBlockName("Samanda - (East Garo Hills Protection Range)").district(district2).range(range7).build(),
//                OperatedBlock.builder().operatedBlockId(13).operatedBlockName("Samanda - (East Garo Hills Protection Range)").district(district2).range(range7).build(), OperatedBlock.builder().operatedBlockId(13).operatedBlockName("Samanda - (East Garo Hills Protection Range)").district(district2).range(range7).build(),

//                OperatedBlock.builder().operatedBlockId(14).operatedBlockName("Samanda - (Northern Nokrek Range)").district(district2).range(range8).build(),
                            OperatedBlock.builder().operatedBlockId(15).operatedBlockName("Rongram").district(district3).range(range8).build(),

//                OperatedBlock.builder().operatedBlockId(16).operatedBlockName("Rongram (DC Park and Nehru Park)").district(district3).range(range9).build(),

                            OperatedBlock.builder().operatedBlockId(17).operatedBlockName("Dadenggre").district(district3).range(range10).build(),
                            OperatedBlock.builder().operatedBlockId(18).operatedBlockName("Tikrikilla").district(district3).range(range10).build(),
                            OperatedBlock.builder().operatedBlockId(19).operatedBlockName("Demdema").district(district3).range(range10).build(),

                            OperatedBlock.builder().operatedBlockId(20).operatedBlockName("Gambegre").district(district3).range(range11).build(),
                            OperatedBlock.builder().operatedBlockId(21).operatedBlockName("Dalu").district(district3).range(range11).build(),
                            OperatedBlock.builder().operatedBlockId(22).operatedBlockName("Selsella").district(district3).range(range11).build(),

                            OperatedBlock.builder().operatedBlockId(23).operatedBlockName("Ampati").district(district4).range(range11).build(),
                            OperatedBlock.builder().operatedBlockId(24).operatedBlockName("Zikzak").district(district4).range(range11).build(),
                            OperatedBlock.builder().operatedBlockId(25).operatedBlockName("Rerapara (Damalgre)").district(district4).range(range11).build(),

                            OperatedBlock.builder().operatedBlockId(26).operatedBlockName("Mawkyrwat").district(district11).range(range12).build(),
                            OperatedBlock.builder().operatedBlockId(27).operatedBlockName("Ranikor").district(district11).range(range12).build(),

                            OperatedBlock.builder().operatedBlockId(28).operatedBlockName("Mawthadraishan").district(district5).range(range12).build(),
                            OperatedBlock.builder().operatedBlockId(29).operatedBlockName("Nongstoin").district(district5).range(range12).build(),
                            OperatedBlock.builder().operatedBlockId(30).operatedBlockName("Mawshynrut").district(district5).range(range12).build(),

                            OperatedBlock.builder().operatedBlockId(31).operatedBlockName("Mairang").district(district6).range(range12).build(),

                            OperatedBlock.builder().operatedBlockId(32).operatedBlockName("Mawphlang").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(33).operatedBlockName("Mawsynram").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(34).operatedBlockName("Shella Bholaganj").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(35).operatedBlockName("Pynursla").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(36).operatedBlockName("Khatarshnong Laitkroh").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(37).operatedBlockName("Mawkynrew").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(38).operatedBlockName("Mawryngkneng").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(39).operatedBlockName("Sohiong").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(40).operatedBlockName("Mawpat").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(41).operatedBlockName("Mawlai").district(district7).range(range13).build(),
                            OperatedBlock.builder().operatedBlockId(42).operatedBlockName("Mylliem").district(district7).range(range13).build(),

//                OperatedBlock.builder().operatedBlockId(43).operatedBlockName("Mylliem (Lady Hydari Park cum Mini Zoo)").district(district7).range(range14).build(),

                            OperatedBlock.builder().operatedBlockId(44).operatedBlockName("Bhoirymbong").district(district8).range(range15).build(),
                            OperatedBlock.builder().operatedBlockId(45).operatedBlockName("Jirang").district(district8).range(range16).build(),
                            OperatedBlock.builder().operatedBlockId(46).operatedBlockName("Umling").district(district8).range(range16).build(),
                            OperatedBlock.builder().operatedBlockId(47).operatedBlockName("Umsning").district(district8).range(range16).build(),

                            OperatedBlock.builder().operatedBlockId(48).operatedBlockName("Thadlaskein").district(district9).range(range17).build(),
                            OperatedBlock.builder().operatedBlockId(49).operatedBlockName("Laskein").district(district9).range(range17).build(),
                            OperatedBlock.builder().operatedBlockId(50).operatedBlockName("Amlarem").district(district9).range(range17).build(),

                            OperatedBlock.builder().operatedBlockId(51).operatedBlockName("Khliehriat").district(district10).range(range18).build(),
                            OperatedBlock.builder().operatedBlockId(52).operatedBlockName("Saipung").district(district10).range(range18).build()
                    ));

            operatedBlockRepository.saveAllAndFlush(operatedBlocks);

//            if(!citizenRepository.existsByPhoneNo("7005852846")){
//                Citizen citizen1=Citizen.builder()
//                        .epicNo("")
//                        .name("Admin1")
//                        .guardianName("Ray")
//                        .email("")
//                        .phoneNo("7005852846")
//                        .state(stateRepository.getReferenceById(17L))
//                        .district(districtRepository.getReferenceById(274L))
//                        .block(blockRepository.getReferenceById(2460L))
//                        .village(villageRepository.getReferenceById(278351L))
//                        .locality("IGP")
//                        .build();
//                citizenRepository.save(citizen1);
//            }
//
//            if(!citizenRepository.existsByPhoneNo("9366964289")) {
//                Citizen citizen2 = Citizen.builder()
//                        .epicNo("")
//                        .name("Admin2")
//                        .guardianName("Ray")
//                        .email("")
//                        .phoneNo("9366964289")
//                        .state(stateRepository.getReferenceById(17L))
//                        .district(districtRepository.getReferenceById(274L))
//                        .block(blockRepository.getReferenceById(2460L))
//                        .village(villageRepository.getReferenceById(278351L))
//                        .locality("IGP")
//                        .build();
//                citizenRepository.save(citizen2);
//            }

        }
    }
}
