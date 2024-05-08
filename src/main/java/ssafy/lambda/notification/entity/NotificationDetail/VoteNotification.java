package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.vote.entity.Vote;

@Entity
@Getter
@DiscriminatorValue("Vote")
public class VoteNotification extends Notification {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    protected VoteNotification() {

    }

    @Builder
    public VoteNotification(Member member, Vote vote) {
        super(member);
        this.vote = vote;
    }

}
