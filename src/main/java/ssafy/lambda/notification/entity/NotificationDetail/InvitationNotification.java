package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import ssafy.lambda.notification.entity.Notification;

@Entity
@Getter
@DiscriminatorValue("Invitation")
public class InvitationNotification extends Notification {

//    초대 테이블 생성 필요
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "invitation_id")
//    private Invitation invitation;

}
