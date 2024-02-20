package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import in.gov.forest.wildlifemis.domian.NotificationType;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;

public interface NotificationTypeServiceInter {
    ApiResponse<?> save(NotificationTypeDTO notificationTypeDTO);
    ApiResponse<?> get();
}
