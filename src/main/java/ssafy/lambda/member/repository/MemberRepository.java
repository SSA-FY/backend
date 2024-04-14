package ssafy.lambda.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.lambda.member.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
