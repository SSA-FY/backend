package ssafy.lambda.invitation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    boolean existsByMemberAndTeam(Member member, Team team);
}
