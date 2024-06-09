package ssafy.lambda.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    Optional<Member> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    List<Member> findAll();

    Optional<Member> findByEmailAndSocial(String email, SocialType social);


    @Query("SELECT m FROM Member m JOIN m.memberships ms WHERE ms.team.teamId = :teamId")
    List<Member> findAllByTeamId(Long teamId);
}
