package ssafy.lambda.board.entity;

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
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;

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
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column
    private Instant expiredAt;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private Long voteId;

    @OneToMany(mappedBy = "expiredVote")
    List<ExpiredVoteInfo> expiredVoteInfoList = new ArrayList<>();


    protected ExpiredVote() {

    }

    /**
     * ExpiredVote은 Vote의 정보를 그대로 가져오므로 All (expired_vote_id 제외) 이때, createdAt와 updatedAt는 Vote 테이블의
     * 것임에 주의한다. 자체적인 createdAt는 존재하지 않아 BaseEntity를 상속받지 않는다.
     */
    @Builder
    public ExpiredVote(String content, String imgUrl, Membership membership,
        Instant expiredAt, Instant createdAt, Instant updatedAt, Long voteId,
        List<ExpiredVoteInfo> expiredVoteInfoList) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.membership = membership;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voteId = voteId;
        this.expiredVoteInfoList = expiredVoteInfoList;
    }


}
