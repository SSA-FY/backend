package ssafy.lambda.membership.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;

@Getter
@Entity
public class Membership {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  protected Membership() {
  }

  @Builder
  public Membership(Long id, Member member, Team team) {
    this.id = id;
    this.member = member;
    this.team = team;
  }
}
