package in.gov.forest.wildlifemis.notification.dto;

import lombok.Builder;
import lombok.Data;

//@Data
//@Builder
public interface ActiveNotification {
     Long getId();
     String getTitle();
     String getCreatedDate();
}
