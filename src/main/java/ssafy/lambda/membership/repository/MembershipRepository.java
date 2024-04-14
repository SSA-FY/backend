package ssafy.lambda.membership.repository;


import org.springframework.data.repository.CrudRepository;
import ssafy.lambda.membership.entity.Membership;

public interface MembershipRepository extends CrudRepository<Membership, Long> {
}
