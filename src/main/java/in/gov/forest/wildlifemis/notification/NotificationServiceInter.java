package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NotificationServiceInter {
    ApiResponse<?> save(MultipartFile file) throws IOException;
    ApiResponse<?> download(String fileName);

}
