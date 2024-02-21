package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NotificationServiceInter {
    ApiResponse<?> save(MultipartFile file, Long notificationTypeId, String title) throws IOException;
    ResponseEntity<?> downloadPDf(Long id);

//    ApiResponse<?> delete(String fileName) throws IOException;
}