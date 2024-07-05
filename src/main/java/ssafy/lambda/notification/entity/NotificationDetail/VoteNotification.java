package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.vote.entity.Vote;

@Entity
@Getter
@DiscriminatorValue("Vote")
public class VoteNotification extends Notification {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vote vote;

    protected VoteNotification() {

    }

    @Builder
    public VoteNotification(Member member, Vote vote) {
        super(member);
        this.vote = vote;
    }

}
