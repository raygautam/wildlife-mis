package in.gov.forest.wildlifemis.externalURL.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalURL_DTO {
    private Long id;
    private String url;
    private String description;
}
