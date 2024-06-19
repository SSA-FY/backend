package ssafy.lambda.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, CustomNotificationRepository {
}
