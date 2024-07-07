package ssafy.lambda.board.repository.Impl;

import static ssafy.lambda.board.entity.QExpiredVote.expiredVote;
import static ssafy.lambda.board.entity.QExpiredVoteInfo.expiredVoteInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import ssafy.lambda.board.dto.ExpiredVoteWrapper;
import ssafy.lambda.board.dto.QExpiredVoteWrapper;
import ssafy.lambda.board.repository.ExpiredVoteInfoRepositoryCustom;

public class ExpiredVoteInfoRepositoryImpl implements ExpiredVoteInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ExpiredVoteInfoRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ExpiredVoteWrapper> findByExpiredVoteIsNull() {
        return queryFactory.select(new QExpiredVoteWrapper(expiredVote, expiredVoteInfo))
                           .from(expiredVoteInfo)
                           .join(expiredVote)
                           .on(expiredVoteInfo.voteId.eq(expiredVoteInfo.voteId))
                           .where(expiredVoteInfo.expiredVote.isNull())
                           .fetch();
    }
}
