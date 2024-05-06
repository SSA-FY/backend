package ssafy.lambda.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class VoteInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

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

    protected VoteInfo() {
    }

    @Builder
    public VoteInfo(Vote vote, Member voter, Member votee) {
        this.vote = vote;
        this.voter = voter;
        this.votee = votee;
        this.opinion = null;
        this.isOpen = false;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
