package in.gov.forest.wildlifemis.notificationType;

import in.gov.forest.wildlifemis.domian.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    List<NotificationType> findByOrderById();
}