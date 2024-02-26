package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;

public interface NotificationTypeServiceInter {
    ApiResponse<?> add(NotificationTypeDTO notificationTypeDTO);
    ApiResponse<?> get();

    ApiResponse<?> update(Long id, NotificationTypeDTO notificationTypeDTO);
}
