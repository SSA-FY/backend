package ssafy.lambda.vote.service;

import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.vote.dto.RequestMemberDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

import java.util.List;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;

public interface VoteService {
    void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId);

    void review(Long memberId, Long voteId, String review);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);
    List<ResponseVoteDto> getUserVote(Long memberId, Long teamId);
}
