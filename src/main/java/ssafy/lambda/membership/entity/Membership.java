package ssafy.lambda.membership.entity;

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
import lombok.Setter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;

@Getter
@Entity
public class Membership extends BaseEntity {

    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MembershipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Setter
    @Column(name = "nickname")
    private String nickname;

    protected Membership() {
    }

    @Builder
    public Membership(Member member, Team team, String nickname) {
        this.member = member;
        this.team = team;
        this.nickname = nickname;
    }
}
