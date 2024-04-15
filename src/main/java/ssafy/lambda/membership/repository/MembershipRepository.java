package ssafy.lambda.membership.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByMember(Member member);

    List<Membership> findByTeam(Team team);
}
