package ssafy.lambda.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    Optional<Member> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    List<Member> findAll();


    @Query("SELECT m FROM Member m JOIN m.memberships WHERE memberships.team.id = :teamId")
    List<Member> findAllByTeamId(Long teamId);
}
