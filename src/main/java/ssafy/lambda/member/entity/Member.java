package ssafy.lambda.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ssafy.lambda.membership.entity.Membership;


@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;

    @Enumerated(EnumType.STRING)
    SocialType social;

    @Column
    String name;

    @Column
    Integer point;

    @Column
    Date createdAt;

    @Column
    String profileImgUrl;

    @Column
    String email;

    @OneToMany(mappedBy = "member")
    List<Membership> memberships = new ArrayList<>();
}
