package ssafy.lambda.vote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class VoteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_info_id")
    private Long id;

    @Column(name = "choosed_user_id")
    private Long choosedUserId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "opinion")
    private String opinion;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    protected VoteInfo(){}

    @Builder
    public VoteInfo(Long id, Long choosedUserId, LocalDateTime createAt, boolean isOpen, String opinion, Vote vote) {
        this.id = id;
        this.choosedUserId = choosedUserId;
        this.createAt = createAt;
        this.isOpen = isOpen;
        this.opinion = opinion;
        this.vote = vote;
    }
}
