package in.gov.forest.wildlifemis.notification.dto;

//@Data
//@Builder

import java.util.Date;

public interface GetNotificationDetails {
    Long getId();
    String getTitle();
    String getCreatedDate();
    String getNotificationTypeName();

    Boolean getIsActive();

    Boolean getIsArchive();
}
