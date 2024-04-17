package ssafy.lambda.vote.repository;

import static ssafy.lambda.membership.entity.QMembership.membership;
import static ssafy.lambda.vote.entity.QVote.vote;
import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
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
     * select ms.id, ms.team_id, ms.member_id, vote.vote_id, vote.content from membership ms join
     * vote on ms.id = vote.id where ms.team_id = 3 and vote.is_proceeding = TRUE;
     *
     * @param
     * @return
     */
    @Override
    public List<ResponseVoteDto> findVoteByMemberIdAndTeamId(Long memberId, Long teamId) {
        QVote voteSub = new QVote("voteSub");
        return queryFactory.select(
                               new QResponseVoteDto(
                                   vote.id.as("voteId"),
                                   vote.membership.team.teamId.as("team_id"),
                                   vote.membership.team.teamName.as("teamName"),
                                   vote.content.as("content"),
                                   vote.imgUrl.as("imgUrl"),
                                   new CaseBuilder().
                                       when(voteInfo.memberId.eq(memberId)).then(true)
                                       .otherwise(false)))
                           .from(voteInfo)
//                           .from(vote) 에러
//                           .leftJoin(vote, voteInfo.vote) //에러
                           .rightJoin(voteInfo.vote, vote)
                           .where(
                               isProceeding(),
                               vote.id.in(
                                   //해당 유저의 선택 그룹에 대한 진행중인 모든 투표의 아이디를 가져온다.
                                   JPAExpressions.select(voteSub.id)
                                                 .from(voteSub)
                                                 .join(voteSub.membership, membership)
                                                 .where(
//                                                     voteSub.membership.team.teamId.eq(teamId)
                                                     teamEq(teamId)
                                                 ))
                           )
                           .fetch();

//        return queryFactory
//            .select(new QResponseVoteDto(
//                membership.team.teamId,
//                vote.content,
//                vote.imgUrl,
//                membership.team.teamName
//            ))
//            .from(vote)
//            .join(vote.membership, membership)
//            .on(vote.membership.eq(membership))
//            .where(
//                membership.eq(membershipParam)
//                          .and(vote.isProceeding.isTrue()))
//            .fetch();
    }

    /**
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     * @param memberId
     * @return BooleanExpression or null
     */
    public BooleanExpression memberEq(Long memberId){
        return memberId != null ? vote.membership.member.memberId.eq(memberId) : null;
    }

    /**
     *
     * vote의 membership.member.memberId와 파라미터가 같은지 확인
     * @param teamId
     * @return BooleanExpression or null
     */
    public BooleanExpression teamEq(Long teamId) {
        return teamId != null ? vote.membership.team.teamId.eq(teamId) : null;
    }

    public BooleanExpression isProceeding() {
        return vote.isProceeding.isTrue();
    }
}