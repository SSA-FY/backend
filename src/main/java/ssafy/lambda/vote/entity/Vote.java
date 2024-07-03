package ssafy.lambda.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;

@Entity
@Getter
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @Column
    private String content;

    @Column
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column
    private Instant expiredAt;

    @OneToMany(mappedBy = "vote", orphanRemoval = true)
    List<VoteInfo> voteInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "vote", orphanRemoval = true)
    List<VoteNotification> notificationList = new ArrayList<>();

    protected Vote() {

    }

    @Builder
    public Vote(String content, String imgUrl, Membership membership) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.membership = membership;
        this.expiredAt = Instant.now()
                                .plus(1, ChronoUnit.DAYS);
    }
}
