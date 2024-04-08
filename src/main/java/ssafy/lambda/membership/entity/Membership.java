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
    @ManyToOne
    Member member;
    @ManyToOne
    Team team;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
