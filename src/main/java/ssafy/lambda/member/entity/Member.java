package ssafy.lambda.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;


@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private SocialType social;

    @Column
    private String name;

    @Column
    private Integer point;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String profileImgUrl;

    @Column
    private String email;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Membership> memberships = new ArrayList<>();

    protected Member() {
    }

    @Builder
    public Member(Long memberId, SocialType social, String name, Integer point,
        String profileImgUrl, String email) {
        this.memberId = memberId;
        this.social = social;
        this.name = name;
        this.point = point;
        this.createdAt = LocalDateTime.now();
        this.profileImgUrl = profileImgUrl;
        this.email = email;
    }

    public void update(String name, Integer point,
        String profileImgUrl) {
        this.name = name;
        this.point = point;
        this.profileImgUrl = profileImgUrl;
    }
}
