package ssafy.lambda.vote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class VoteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_info_id")
    private Long id;

    @Column(name = "choosed_member_id")
    private Long choosedMemberId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "opinion")
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @Column(name = "member_id")
//    private Member member;
    private Long memberId;

    protected VoteInfo(){}

    @Builder
    public VoteInfo(Long choosedMemberId, Vote vote, Long memberId) {
        this.choosedMemberId = choosedMemberId;
        this.createAt = LocalDateTime.now();
        this.isOpen = false;
        this.vote = vote;
        this.memberId = memberId;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
