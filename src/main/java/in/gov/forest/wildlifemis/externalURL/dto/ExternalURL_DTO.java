package in.gov.forest.wildlifemis.externalURL.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalURL_DTO {
    private Long id;

//    @NotBlank(message = "url must not be empty or null")
    private String url;

//    @NotBlank(message = "description must not be empty or null")
    private String description;
}
