package ssafy.lambda.vote.service;

import java.util.List;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;

public interface VoteService {
    void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId);

    void review(Long memberId, Long voteId, String review);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);
}
