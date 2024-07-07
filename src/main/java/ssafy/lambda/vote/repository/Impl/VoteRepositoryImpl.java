package ssafy.lambda.vote.repository.Impl;

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
import ssafy.lambda.vote.repository.VoteRepositoryCustom;

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
                                                 .where(voteSub.team.eq(team))
                               )
                           )
                           .orderBy(new OrderSpecifier(Order.ASC, voteInfo.voter).nullsFirst(),
                               new OrderSpecifier(Order.ASC, vote.expiredAt),
                               new OrderSpecifier(Order.ASC, vote.id))
                           .fetch();
    }


    /**
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     *
     * @param team
     * @return
     */
    public BooleanExpression teamEq(Team team) {
        return team != null ? vote.team.eq(team) : null;
    }

    public BooleanExpression isProceeding() {
        return vote.expiredAt.after(Instant.now());
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

    /**
     * 멤버가 속한 팀 리스트를 받아, 아직 투표하지 않은 투표를 가지고 잇는 팀을 반환 만료 시간 가까운 것 우선
     *
     * @param member
     * @param teamList
     * @return
     */
    @Override
    public List<Team> findByMemberAndVoteWhether(Member member, List<Team> teamList) {
        // 멤버가 이미 투표한 vote_id를 찾기
        List<Long> votedIds = queryFactory
            .select(voteInfo.vote.id)
            .from(voteInfo)
            .where(voteInfo.voter.eq(member))
            .fetch();

        // 멤버가 속한 팀의 모든 투표 중 아직 투표하지 않은 투표를 찾기
        return queryFactory
            .select(
                vote.team
            )
            .from(vote)
            .where(vote.team.in(teamList)
                            .and(vote.id.notIn(votedIds))
                            .and(isProceeding()))
            .groupBy(vote.team)
            .orderBy(vote.expiredAt.asc())
            .fetch();
    }

    @Override
    public Long deleteVoteByExpiredVote(List<Long> voteIds) {
        return queryFactory.delete(vote)
                           .where(vote.id.in(voteIds))
                           .execute();
    }
}