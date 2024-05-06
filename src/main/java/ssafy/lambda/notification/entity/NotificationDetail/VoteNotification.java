package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.*;
import lombok.Getter;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.vote.entity.Vote;

@Entity
@Getter
@DiscriminatorValue("Vote")
public class VoteNotification extends Notification {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;
}
