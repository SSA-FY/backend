package ssafy.lambda.vote.repository;

import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.vote.dto.ResponseTodayVoteInfoDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

public interface VoteInfoRepositoryCustom {

    List<VoteInfo> findVoteInfoToMeListByMemberAndVote(Member member, Vote vote);

    ResponseTodayVoteInfoDto findTodayVoteInfoByMember(Member member);
}
