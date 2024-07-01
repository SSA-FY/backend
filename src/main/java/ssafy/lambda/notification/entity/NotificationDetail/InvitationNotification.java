package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.Notification;

@Entity
@Getter
@DiscriminatorValue("Invitation")
public class InvitationNotification extends Notification {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    protected InvitationNotification() {
    }

    @Builder
    public InvitationNotification(Member member, Invitation invitation) {
        super(member);
        this.invitation = invitation;
    }
}
