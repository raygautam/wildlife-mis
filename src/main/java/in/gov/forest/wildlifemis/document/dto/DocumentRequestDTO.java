package in.gov.forest.wildlifemis.document.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentRequestDTO {
    private Long documentTypeId;
    private String title;
//    private LocalDateTime publishedDate;
}
