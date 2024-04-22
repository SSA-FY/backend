package ssafy.lambda.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.lang.management.MemoryManagerMXBean;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class VoteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choosed_member_id")
    private Member choosedMember;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "is_open", columnDefinition="boolean default false", nullable = false)
    private boolean isOpen;

    @Column(name = "opinion")
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected VoteInfo(){}

    @Builder
    public VoteInfo(Member choosedMember, Vote vote, Member member) {
        this.choosedMember = choosedMember;
        this.createAt = LocalDateTime.now();
        this.vote = vote;
        this.member = member;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
