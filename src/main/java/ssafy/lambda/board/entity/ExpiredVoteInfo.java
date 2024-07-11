package ssafy.lambda.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class ExpiredVoteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expired_vote_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    private Member voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "votee_id")
    private Member votee;

    @Column
    private String opinion;

    @Column
    private Boolean isOpen;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private Long voteId;


    protected ExpiredVoteInfo() {
    }

    public void addExpiredVote(ExpiredVote expiredVote){
        this.expiredVote = expiredVote;
        expiredVote.getExpiredVoteInfoList().add(this);
    }

    /**
     * ExpiredVoteInfo는 VoteInfo의 정보를 그대로 가져오므로 All (expired_vote_info_id 제외) 이때, createdAt와
     * updatedAt는 VoteInfo 테이블의 것임에 주의한다. 자체적인 createdAt는 존재하지 않아 BaseEntity를 상속받지 않는다.
     */
    @Builder
    public ExpiredVoteInfo(ExpiredVote expiredVote, Member voter, Member votee, String opinion,
        Boolean isOpen, Instant createdAt, Instant updatedAt, Long voteId) {
        this.expiredVote = expiredVote;
        this.voter = voter;
        this.votee = votee;
        this.opinion = opinion;
        this.isOpen = isOpen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voteId = voteId;
    }

    // 만료된 투표도 Open상태 변경 가능.
    public void setOpen(boolean open) {
        isOpen = open;
    }
}
