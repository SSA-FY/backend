package ssafy.lambda.team.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
