package ssafy.lambda.board.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssafy.lambda.board.entity.ExpiredVote;

public interface ExpiredVoteRepository extends JpaRepository<ExpiredVote, Long>,
    BoardRepositoryCustom {


    @Query(
        "select ev "
            + "from ExpiredVote ev "
            + "where ev.membership.team.teamId = :teamId "
            + "order by ev.id DESC"
    )
    List<ExpiredVote> findAllByTeamId(Long teamId, Pageable pageable);

}
