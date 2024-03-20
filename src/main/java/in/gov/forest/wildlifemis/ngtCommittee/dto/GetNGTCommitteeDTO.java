package in.gov.forest.wildlifemis.ngtCommittee.dto;

public record GetNGTCommitteeDTO (
         Long id,
         String title,
         String createdDate,
         String ngtCommitteeTypeName,
         boolean isActive,
         String publishedDate
){

}
