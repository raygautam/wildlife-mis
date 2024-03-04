package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NotificationServiceInter {
    ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title) throws IOException;
    ResponseEntity<?> downloadPDf(Long id) throws IOException;

    ApiResponse<?> getActiveNotification(Long notificationTypeId);

    ApiResponse<?> archive(Long id);

    ApiResponse<?> getAllArchive();

    ApiResponse<?> getAllNotification();

    ApiResponse<?> getArchiveNotification(Long notificationTypeId);

    ApiResponse<?> getArchiveNotificationByPagination(Long notificationTypeId, Pageable pageable);

//    ApiResponse<?> delete(String fileName) throws IOException;
}
