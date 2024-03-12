package in.gov.forest.wildlifemis.notification.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetNotificationDetailsDTO {
    private Long id;
    private String title;
    private String createdDate;
    private String notificationTypeName;
    private boolean isActive;
    private boolean isArchive;
}
