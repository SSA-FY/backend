package ssafy.lambda.vote.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.vote.dto.QResponseTodayVoteInfoDto;
import ssafy.lambda.vote.dto.ResponseTodayVoteInfoDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

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

    @Override
    public ResponseTodayVoteInfoDto findTodayVoteInfoByMember(Member member) {
        LocalDate today = LocalDate.now();
        Instant startOfDay = today.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = today.plusDays(1)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant();
        return queryFactory.select(new QResponseTodayVoteInfoDto(voteInfo.count(), voteInfo.opinion.count()))
                           .from(voteInfo)
                           .where(voteInfo.createdAt.between(startOfDay, endOfDay),
                                   voteInfo.voter.eq(member))
                           .groupBy(voteInfo.voter)
                           .fetchOne();
    }
}
