package ssafy.lambda.board.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.team.entity.Team;

@Entity
@Getter
public class ExpiredVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expired_vote_id")
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

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private Long voteId;

    @OneToMany(mappedBy = "expiredVote", orphanRemoval = true)
    List<ExpiredVoteInfo> expiredVoteInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "expiredVote", cascade = CascadeType.ALL, orphanRemoval = true)
    List<VoteNotification> notificationList = new ArrayList<>();

    protected ExpiredVote() {

    }

    /**
     * ExpiredVote은 Vote의 정보를 그대로 가져오므로 All (expired_vote_id 제외) 이때, createdAt와 updatedAt는 Vote 테이블의
     * 것임에 주의한다. 자체적인 createdAt는 존재하지 않아 BaseEntity를 상속받지 않는다.
     */
    @Builder
    public ExpiredVote(String content, String imgUrl, Team team,
        Instant expiredAt, Instant createdAt, Instant updatedAt, Long voteId,
        List<ExpiredVoteInfo> expiredVoteInfoList) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.team = team;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voteId = voteId;
        this.expiredVoteInfoList = expiredVoteInfoList;
    }


}
