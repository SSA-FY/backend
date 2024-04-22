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
import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.QResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.QVote;

public class VoteRepositoryImpl implements VoteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public VoteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * MemberShipList를 이용하여 해당 유저의 모든 VoteList반환
     * <p>
     * @param
     * @return
     */
    @Override
    public List<ResponseVoteDto> findVoteByMemberIdAndTeamId(Member member, Team team) {
        QVote voteSub = new QVote("voteSub");
        return queryFactory.select(
                               new QResponseVoteDto(
                                   vote.id.as("voteId"),
                                   vote.membership.team.teamId.as("team_id"),
                                   vote.membership.team.teamName.as("teamName"),
                                   vote.content.as("content"),
                                   vote.imgUrl.as("imgUrl"),
                                   new CaseBuilder().
                                       when(voteInfo.member.eq(member))
                                       .then(true)
                                       .otherwise(false)))
                           .from(voteInfo)
                           .rightJoin(voteInfo.vote, vote)
                           .on(voteInfo.member.eq(member))
                           .where(
                               isProceeding(),
                               vote.id.in(
                                   //해당 유저의 선택 그룹에 대한 진행중인 모든 투표의 아이디를 가져온다.
                                   JPAExpressions.select(voteSub.id)
                                                 .from(voteSub)
                                                 .join(voteSub.membership, membership)
                                                 .where(
                                                     teamEq(team)
                                                 ))
                           )
                           .orderBy(new OrderSpecifier(Order.ASC, voteInfo.member).nullsFirst(),
                               new OrderSpecifier(Order.ASC, vote.expiredAt),
                               new OrderSpecifier(Order.ASC, vote.id))
                           .fetch();
    }

    /**
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     * @param member
     * @return
     */
    public BooleanExpression memberEq(Member member){
        return member != null ? vote.membership.member.eq(member) : null;
    }

    /**
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     * @param team
     * @return
     */
    public BooleanExpression teamEq(Team team) {
        return team != null ? vote.membership.team.eq(team) : null;
    }

    public BooleanExpression isProceeding() {
        return vote.isProceeding.isTrue();
    }
}