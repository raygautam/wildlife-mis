package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<in.gov.forest.wildlifemis.domian.Notification, Long> {

    List<GetNotificationDetails> findByNotificationTypeIdAndIsActive(Long notificationTypeId, boolean isActive);

    List<GetNotificationDetails> findByIsArchive(Boolean aTrue);

//    @Query("SELECT n FROM Notification n")
    List<GetNotificationDetails> findByOrderByCreatedDateDesc();

    List<GetNotificationDetails> findByNotificationTypeIdAndIsArchive(Long notificationTypeId, Boolean aTrue);
}