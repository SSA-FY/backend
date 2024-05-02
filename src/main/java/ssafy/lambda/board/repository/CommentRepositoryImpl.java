package ssafy.lambda.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ssafy.lambda.board.entity.VoteComment;

// comment 관련 구현 : 클래스명 주의!!
public class CommentRepositoryImpl implements BoardRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /* saveComment
     * JpaRepository.save(voteComment) 로 update할때 select되는 상황
     * 쿼리 최적화를 위해 생성
     * */
    @Override
    public void saveComment(VoteComment voteComment) {
        entityManager.persist(voteComment);
    }
}
