package ssafy.lambda.notification.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.vote.entity.Vote;

@Entity
@Getter
public class Notification extends BaseEntity {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "title")
    private String title;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

//    초대 테이블 생성 필요
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "invitation_id")
//    private Invitation invitation;


    public Notification() {
    }

    @Builder
    public Notification(NotificationType notificationType, String title, Vote vote, ExpiredVote expiredVote) {
        this.notificationType = notificationType;
        this.title = title;
        this.vote = vote;
        this.expiredVote = expiredVote;
    }
}
