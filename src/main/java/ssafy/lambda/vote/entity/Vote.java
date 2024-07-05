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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.team.entity.Team;

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
    @JoinColumn(name = "team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @Column
    private Instant expiredAt;

    @OneToMany(mappedBy = "vote", orphanRemoval = true)
    List<VoteInfo> voteInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "vote", orphanRemoval = true)
    List<VoteNotification> notificationList = new ArrayList<>();

    protected Vote() {

    }

    @Builder
    public Vote(String content, String imgUrl, Team team) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.team = team;
        this.expiredAt = Instant.now()
                                .plus(1, ChronoUnit.DAYS);
    }
}
