package ssafy.lambda.membership.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.lambda.group.entity.Team;
import ssafy.lambda.member.entity.Member;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Membership {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
