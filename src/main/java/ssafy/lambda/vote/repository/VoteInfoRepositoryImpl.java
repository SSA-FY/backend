package ssafy.lambda.vote.repository;

import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

public class VoteInfoRepositoryImpl implements VoteInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    public VoteInfoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //해당 투표에 대해 해당 멤버가 받은 투표 정보를 반환
    //열린 투표 정보 우선, 한줄 평 길이 긴 것 우선
    @Override
    public List<VoteInfo> findVoteInfoToMeListByMemberAndVote(Member member, Vote vote) {
        return queryFactory.select(voteInfo)
                           .from(voteInfo)
                           .where(voteInfo.vote.eq(vote), voteInfo.votee.eq(member))
                           .orderBy(voteInfo.isOpen.asc(), voteInfo.opinion.length()
                                                                           .desc())
                           .fetch();
    }
}
