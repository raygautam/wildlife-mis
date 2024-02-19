package in.gov.forest.wildlifemis.helper;


import in.gov.forest.wildlifemis.lgdEntities.entities.Block;
import in.gov.forest.wildlifemis.lgdEntities.entities.SubDistrict;
import in.gov.forest.wildlifemis.lgdEntities.repository.BlockRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MergeBlockWithSubDistrict {

    private final BlockRepository blockRepository;

    public MergeBlockWithSubDistrict(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }


    public List<SubDistrict> mergeListsToMap(List<Block> blocks, List<SubDistrict> subDistricts) {
        List<SubDistrict> mergedMap = new ArrayList<>();
        for (SubDistrict subDistrict : subDistricts) {
            for (Block block : blocks) {
                double similarityThreshold = 80.0;
                boolean isMatch = fuzzyMatch(block.getBlockName(), subDistrict.getSubDistrictName(), similarityThreshold);
//                double similarity = StringUtils.getJaroWinklerDistance(block.getBlockName(), subDistrict.getSubDistrictName());
                if (isMatch) {
                    subDistricts.forEach(subDistrict1 -> {
                        SubDistrict subDistrict2=SubDistrict.builder()
                                .subDistrictCode(subDistrict.getSubDistrictCode())
                                .subDistrictName(subDistrict.getSubDistrictName())
                                .block(blockRepository.getReferenceById(block.getBlockCode()))
                                .district(subDistrict.getDistrict())
                                .build();
                        mergedMap.add(subDistrict2);
                    });
                    break; // Assuming only one matching subDistrict is expected
                }
            }
        }

        return mergedMap;
    }

    public static boolean fuzzyMatch(String str1, String str2, double similarityThreshold) {
        double similarity = calculateSimilarity(str1, str2);
        return similarity >= similarityThreshold;
    }

    private static double calculateSimilarity(String str1, String str2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(str1.toLowerCase(), str2.toLowerCase());
        int maxLength = Math.max(str1.length(), str2.length());
        return (1 - (double) distance / maxLength) * 100;
    }
}
