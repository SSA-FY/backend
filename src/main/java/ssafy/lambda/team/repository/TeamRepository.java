package ssafy.lambda.team.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.lambda.team.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    Optional<Team> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"memberships"})
    List<Team> findAll();

    Optional<Team> findByTeamName(String name);

    @Query("select t from Team t where t.teamName like concat('%', :teamName, '%')")
    List<Team> findByTeamNameLike(@Param("teamName") String teamName);

    @Query("SELECT t FROM Team t JOIN t.memberships ms WHERE ms.member.memberId = :memberId")
    List<Team> findAllByMemberId(Long memberId);
}
