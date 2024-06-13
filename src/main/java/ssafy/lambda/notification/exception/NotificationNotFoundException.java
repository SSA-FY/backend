package ssafy.lambda.notification.exception;

/**
 * 해당 알림이 존재하지 않을 때 발생하는 예외
 */
public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException() {
        super("존재하지 않는 알림입니다.");
    }
}
