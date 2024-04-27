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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import ssafy.lambda.membership.entity.Membership;

@Entity
@Getter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "is_proceeding", columnDefinition="boolean default true", nullable = false)
    private boolean isProceeding;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "vote")
    List<VoteInfo> voteInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    protected Vote() {

    }

    @Builder
    public Vote(String content, String imgUrl, Membership membership) {
        this.content = content;
        this.createAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusDays(7);
        this.imgUrl = imgUrl;
        this.membership = membership;
    }
}
