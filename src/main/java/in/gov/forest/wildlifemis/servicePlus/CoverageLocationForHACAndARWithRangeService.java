package in.gov.forest.wildlifemis.servicePlus;


import in.gov.forest.wildlifemis.lgdEntities.entities.Range;
import in.gov.forest.wildlifemis.lgdEntities.repository.RangeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoverageLocationForHACAndARWithRangeService {

    @Autowired
    private RangeRepository rangeRepository;

    @Value("${CoverageLocationForHACAndARWithRangeService.value}")
    private Boolean CoverageLocationForHACAndARWithRangeServiceValue;

    @Autowired
    private CoverageLocationForHACAndARWithRangeRepository coverageLocationForHACAndARRepository;

    @PostConstruct
    public void saveCoverageLocationForHACAndARForHACAndAR() {
        if(CoverageLocationForHACAndARWithRangeServiceValue){
            Range range1 = rangeRepository.getReferenceById(2360260);
            Range range2 = rangeRepository.getReferenceById(2360261);
            Range range3 = rangeRepository.getReferenceById(2360262);
            Range range4 = rangeRepository.getReferenceById(2360263);
            Range range5 = rangeRepository.getReferenceById(2360264);
            Range range6 = rangeRepository.getReferenceById(2360265);
            Range range7 = rangeRepository.getReferenceById(2360266);
            Range range8 = rangeRepository.getReferenceById(2360267);
            Range range9 = rangeRepository.getReferenceById(2360268);
            Range range10 = rangeRepository.getReferenceById(2360269);
            Range range11 = rangeRepository.getReferenceById(2360270);
            Range range12 = rangeRepository.getReferenceById(2360271);
            Range range13 = rangeRepository.getReferenceById(2360272);
            Range range14 = rangeRepository.getReferenceById(2360273);
            Range range15 = rangeRepository.getReferenceById(2360274);
            Range range16 = rangeRepository.getReferenceById(2360275);
            Range range17 = rangeRepository.getReferenceById(2360470);
            Range range18 = rangeRepository.getReferenceById(2360471);

            List<CoverageLocationForHACaAndARWithRange> coverageLocationList = new ArrayList<>();
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(1).hmcCoverageLocation(1633450).arCoverageLocation(1633468).range(range1).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(2).hmcCoverageLocation(1633439).arCoverageLocation(1633457).range(range2).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(3).hmcCoverageLocation(1633440).arCoverageLocation(1633458).range(range3).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(4).hmcCoverageLocation(1633448).arCoverageLocation(1633466).range(range4).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(5).hmcCoverageLocation(1633435).arCoverageLocation(1633451).range(range5).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(6).hmcCoverageLocation(1633441).arCoverageLocation(1633459).range(range6).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(7).hmcCoverageLocation(1633437).arCoverageLocation(1633455).range(range7).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(8).hmcCoverageLocation(1633445).arCoverageLocation(1633463).range(range8).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(9).hmcCoverageLocation(1633443).arCoverageLocation(1633460).range(range9).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(10).hmcCoverageLocation(1633447).arCoverageLocation(1633465).range(range10).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(11).hmcCoverageLocation(1633449).arCoverageLocation(1633467).range(range11).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(12).hmcCoverageLocation(1633436).arCoverageLocation(1633453).range(range12).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(13).hmcCoverageLocation(1633444).arCoverageLocation(1633462).range(range13).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(14).hmcCoverageLocation(1633442).arCoverageLocation(1633461).range(range14).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(15).hmcCoverageLocation(1633438).arCoverageLocation(1633456).range(range15).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(16).hmcCoverageLocation(1633446).arCoverageLocation(1633464).range(range16).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(17).hmcCoverageLocation(1633482).arCoverageLocation(1633454).range(range17).build());
            coverageLocationList.add(CoverageLocationForHACaAndARWithRange.builder().id(18).hmcCoverageLocation(1633481).arCoverageLocation(1633452).range(range18).build());

            coverageLocationForHACAndARRepository.saveAllAndFlush(coverageLocationList);

        }

    }
}
