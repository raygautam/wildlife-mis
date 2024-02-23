//package in.gov.forest.wildlifemis.notification.dto;
//
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//@Data
//@Builder
//public class AddNotificationRequest {
//    @NotNull(message = "Select file!")
//    private MultipartFile file;
//    @NotNull(message = "Provide notificationTypeId!")
//    private Long notificationTypeId;
//    @NotEmpty(message = "Provide title!")
//    private String title;
//}
