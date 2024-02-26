//package in.gov.forest.wildlifemis.mapper;
//
//import in.gov.forest.wildlifemis.domian.NotificationType;
//import in.gov.forest.wildlifemis.notificationType.dto.NotificationTypeDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class NotificationTypeMapper {
//
//    public static NotificationType convertDTOToNotificationType(NotificationTypeDTO notificationTypeDTO){
//        return NotificationType.builder()
//                .name(notificationTypeDTO.getName())
//                .build();
//    }
//
//    public static NotificationTypeDTO convertNotificationTypeToDTO(NotificationType notificationType){
//        return NotificationTypeDTO.builder()
//                .id(notificationType.getId())
//                .name(notificationType.getName())
//                .build();
//    }
//}
