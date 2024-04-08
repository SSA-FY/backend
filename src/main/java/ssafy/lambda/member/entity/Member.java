package ssafy.lambda.member.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ssafy.lambda.membership.entity.Membership;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Member {
    public enum SocialType {
        Google, Kakao;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;
    @Enumerated(EnumType.STRING)
    SocialType social;
    @Column
    String name;
    @OneToMany
    List<Membership> memberships;
}
