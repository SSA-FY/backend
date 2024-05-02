package ssafy.lambda.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class ExpiredVoteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expired_vote_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choosed_member_id")
    private Member choosedMember;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "opinion")
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected ExpiredVoteInfo() {
    }


    // voteInfo를 그대로 가져오므로 All
    @Builder
    public ExpiredVoteInfo(Long id, Member choosedMember, LocalDateTime createAt, boolean isOpen,
        String opinion, ExpiredVote expiredVote, Member member) {
        this.id = id;
        this.choosedMember = choosedMember;
        this.createAt = createAt;
        this.isOpen = isOpen;
        this.opinion = opinion;
        this.expiredVote = expiredVote;
        this.member = member;
    }

    // 만료된 투표도 Open상태 변경 가능.
    public void setOpen(boolean open) {
        isOpen = open;
    }
}
