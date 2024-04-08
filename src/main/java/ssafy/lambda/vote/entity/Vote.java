package ssafy.lambda.vote.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "is_proceeding")
    private boolean isProceeding;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "vote")
    List<VoteInfo> voteInfoList = new ArrayList<>();

    protected Vote() {
    }


    @Builder
    public Vote(Long id, String content, LocalDateTime createAt, LocalDateTime expiredAt, boolean isProceeding, String imgUrl) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.isProceeding = isProceeding;
        this.imgUrl = imgUrl;
    }
}
