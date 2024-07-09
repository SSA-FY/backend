package ssafy.lambda.notification.entity.NotificationDetail;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.vote.entity.Vote;

@Entity
@Getter
@Setter
@DiscriminatorValue("Vote")
public class VoteNotification extends Notification {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

    @Column(name = "is_expired")
    private Boolean isExpired;


    protected VoteNotification() {

    }

    @Builder
    public VoteNotification(Member member, Vote vote, ExpiredVote expiredVote, Boolean isExpired) {
        super(member);
        this.vote = vote;
        this.expiredVote = expiredVote;
        this.isExpired = isExpired;
    }

}
