package ssafy.lambda.vote.service;

import ssafy.lambda.vote.dto.RequestMemberDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;

import java.util.List;

public interface VoteService {
    void createVote(RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId);
}
