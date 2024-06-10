package ssafy.lambda.vote.repository;

import static ssafy.lambda.membership.entity.QMembership.membership;
import static ssafy.lambda.vote.entity.QVote.vote;
import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.Instant;
import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.QResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.QVote;
import ssafy.lambda.vote.entity.Vote;

public class VoteRepositoryImpl implements VoteRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public VoteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * MemberShipList를 이용하여 해당 멤버의 모든 VoteList반환
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<ResponseVoteDto> findVoteByVoterAndTeam(Member voter, Team team) {
        QVote voteSub = new QVote("voteSub");
        return queryFactory.select(
                               new QResponseVoteDto(
                                   vote.id.as("voteId"),
                                   vote.content.as("content"),
                                   vote.imgUrl.as("imgUrl"),
                                   new CaseBuilder().
                                       when(voteInfo.voter.eq(voter))
                                       .then(true)
                                       .otherwise(false)))
                           .from(voteInfo)
                           .rightJoin(voteInfo.vote, vote)
                           .on(voteInfo.voter.eq(voter))
                           .where(
                               isProceeding(),
                               vote.id.in(
                                   //해당 멤버의 선택 팀에 대한 진행중인 모든 투표의 아이디를 가져온다.
                                   JPAExpressions.select(voteSub.id)
                                                 .from(voteSub)
                                                 .join(voteSub.membership, membership)
                                                 .where(
                                                     teamEq(team)
                                                 ))
                           )
                           .orderBy(new OrderSpecifier(Order.ASC, voteInfo.voter).nullsFirst(),
                               new OrderSpecifier(Order.ASC, vote.expiredAt),
                               new OrderSpecifier(Order.ASC, vote.id))
                           .fetch();
    }

    /**
     * 현재 진행 중인 투표에 대해 멤버가 아직 투표하지 않은 투표를 가져오며, 이를 팀을 통해 해당 팀으로 필터링한다. 최적화 : 하나의 팀에 대해서를 모든 팀에 대해서
     * 1번 쿼리를 날릴 수 있도록
     *
     * @param voter
     * @param team
     * @return
     */
    @Override
    public Vote findInCompleteVoteByVoterAndTeam(Member voter, Team team) {
        BooleanExpression inMembership = vote.membership.in(
            JPAExpressions.select(membership)
                          .from(membership)
                          .where(membership.team.eq(team))
        );

        BooleanExpression notInVoteInfo = vote.notIn(
            JPAExpressions.select(voteInfo.vote)
                          .from(voteInfo)
                          .where(voteInfo.voter.eq(voter))
        );

        return queryFactory
            .selectFrom(vote)
            .where(isProceeding().and(inMembership)
                                 .and(notInVoteInfo))
            .orderBy(vote.expiredAt.asc()) //가장 빨리 끝나는 1개의 레코드만 가져온다
            .limit(1)
            .fetchOne();
    }


    /**
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     *
     * @param team
     * @return
     */
    public BooleanExpression teamEq(Team team) {
        return team != null ? vote.membership.team.eq(team) : null;
    }

    public BooleanExpression isProceeding() {
        return vote.expiredAt.before(Instant.now());
    }


    public List<Object[]> findVoteInfoByCnt(Long voteId) {

        String sql =
            " SELECT vi.votee.id, COUNT(*) as cnt, ROUND(1.0 * COUNT(*) / SUM(COUNT(*)) OVER (), 2) "
                + "FROM VoteInfo vi "
                + "WHERE vi.vote.id = :voteId "
                + "GROUP BY vi.votee.id "
                + "ORDER BY cnt DESC LIMIT 6";

        Query query = entityManager.createQuery(sql);
        query.setParameter("voteId", voteId);

        List<Object[]> resultOfQuery = query.getResultList();

        return resultOfQuery;
    }
}