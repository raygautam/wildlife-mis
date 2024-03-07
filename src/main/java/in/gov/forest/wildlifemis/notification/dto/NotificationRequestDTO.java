package in.gov.forest.wildlifemis.notification.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequestDTO {
    private Long notificationTypeId;
    private String title;
}
