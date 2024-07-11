package ssafy.lambda.board.repository.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import ssafy.lambda.board.repository.BoardRepositoryCustom;

public class ExpiredVoteRepositoryImpl implements BoardRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findTopMemberByBoard(Long expiredVoteId) {

        String sql =
            " SELECT evi.votee.id, evi.votee.profileImgUrl, COUNT(*) as cnt "
                + "FROM ExpiredVoteInfo evi "
                + "WHERE evi.expiredVote.id = :expiredVoteId "
                + "GROUP BY evi.votee.id "
                + "ORDER BY cnt DESC LIMIT 6";

        Query query = entityManager.createQuery(sql);
        query.setParameter("expiredVoteId", expiredVoteId);

        return query.getResultList();
    }

    @Override
    public Long getCntByBoard(Long expiredVoteId) {
        String sql =
            "SELECT COUNT(*) as cnt "
                + "FROM ExpiredVoteInfo evi "
                + "WHERE evi.expiredVote.id = :expiredVoteId ";

        Query query = entityManager.createQuery(sql);
        query.setParameter("expiredVoteId", expiredVoteId);

        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public List<Object[]> findMemberCntByBoard(Long expiredVoteId, Long page) {

        String sql =
            " SELECT evi.votee.id, evi.votee.tag, evi.votee.profileImgUrl, COUNT(*) as cnt "
                + "FROM ExpiredVoteInfo evi "
                + "WHERE evi.expiredVote.id = :expiredVoteId "
                + "GROUP BY evi.votee.id "
                + "ORDER BY cnt DESC ";

        Query query = entityManager.createQuery(sql);
        query.setParameter("expiredVoteId", expiredVoteId);
        query.setFirstResult(page.intValue() * 6); //offset 설정
        query.setMaxResults(6); //limit 설정
        return query.getResultList();
    }


}
