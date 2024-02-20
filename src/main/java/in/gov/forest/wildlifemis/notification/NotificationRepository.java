package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.domian.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}