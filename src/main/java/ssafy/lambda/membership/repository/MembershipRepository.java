package ssafy.lambda.membership.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("SELECT m FROM Membership m JOIN FETCH m.member JOIN FETCH m.team WHERE m.member = :member")
    List<Membership> findByMember(@Param("member") Member member);

    @Query("SELECT m FROM Membership m JOIN FETCH m.member WHERE m.team = :team")
    List<Membership> findByTeam(@Param("team") Team team);

    Optional<Membership> findByMemberAndTeam(Member member, Team team);

    @Query("select m from Membership m where m.member.memberId = :memberId and m.team.teamId = :teamId")
    Optional<Membership> findByMemberIdAndTeamId(@Param("memberId") UUID memberId,
        @Param("teamId") Long teamId);

    boolean existsByTeamAndNickname(Team team, String nickname);

    boolean existsByMemberAndTeam(Member member, Team team);
}
