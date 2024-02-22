package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.notification.dto.ActiveNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    @Query("SELECT n.id, n.title, n.createdDate From Notification n  WHERE n.notificationType.id = ?1 AND n.isActive = ?2")
    List<ActiveNotification> findByNotificationTypeIdAndIsActive(Long notificationTypeId, boolean isActive);
}