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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ssafy.lambda.membership.entity.Membership;


@Getter
@Entity
public class Member implements OAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 16)
    private UUID memberId;

    @Enumerated(EnumType.STRING)
    private SocialType social;

    @Setter
    @Column
    private String name;

    @Column(unique = true)
    private String id;

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

    @Setter
    @Column
    private String refreshToken;

    protected Member() {
    }

    @Builder
    public Member(UUID memberId, SocialType social, String name, Integer point,
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

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
            new SimpleGrantedAuthority("ROLE_USER"));
    }
}
