package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.Notification;

@Entity
@Getter
@DiscriminatorValue("ExpiredVote")
public class ExpiredVoteNotification extends Notification {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

    protected ExpiredVoteNotification(){

    }

    @Builder
    public ExpiredVoteNotification(Long id, Member member, ExpiredVote expiredVote) {
        super(id, member);
        this.expiredVote = expiredVote;
    }
}
