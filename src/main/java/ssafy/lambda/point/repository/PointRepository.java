package ssafy.lambda.point.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ssafy.lambda.point.entity.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("SELECT p FROM Point p JOIN p.member m WHERE m.memberId = :memberId")
    List<Point> findAllByMemberId(UUID memberId);
}
