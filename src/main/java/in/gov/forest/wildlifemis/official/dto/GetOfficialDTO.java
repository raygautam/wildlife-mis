package in.gov.forest.wildlifemis.official.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOfficialDTO {
    private Long id;
    private String name;
    private String designation;
    private byte[] image;
}
