package in.gov.forest.wildlifemis.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequestDTO {
    private Long notificationTypeId;
    private String title;
//    private String publishedDate;
}
