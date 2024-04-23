package ssafy.lambda.membership.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByMember(Member member);

    List<Membership> findByTeam(Team team);


    @Query("select m from Membership m where m.member.memberId = :memberId and m.team.teamId = :teamId")
    Optional<Membership> findByMemberIdAndTeamId(@Param("memberId") Long memberId, @Param("teamId") Long teamId);

}
