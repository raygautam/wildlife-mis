package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.notification.dto.ActiveNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<ActiveNotification> findByNotificationTypeIdAndIsActive(Long notificationTypeId, boolean isActive);

    List<ActiveNotification> findByIsArchive(Boolean aTrue);
}