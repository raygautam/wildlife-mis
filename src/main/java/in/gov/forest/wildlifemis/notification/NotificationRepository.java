package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.notification.dto.GetNotificationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<in.gov.forest.wildlifemis.domian.Notification, Long> {

    List<Notification> findByNotificationTypeIdAndIsActiveOrderByCreatedDateDesc(Long notificationTypeId, boolean isActive);

    List<Notification> findByIsArchiveOrderByCreatedDateDesc(Boolean aTrue);

//    @Query("SELECT n FROM Notification n")
//    List<GetNotificationDetails> findByOrderByCreatedDateDesc();

    List<Notification> findByNotificationTypeIdAndIsArchiveOrderByCreatedDateDesc(Long notificationTypeId, Boolean aTrue);
    Page<Notification> findByNotificationTypeIdAndIsArchiveOrderByCreatedDateDesc(Long notificationTypeId, Boolean aTrue, Pageable pageable);

    List<Notification> findByIsActiveOrderByCreatedDateDesc(Boolean aTrue);
}