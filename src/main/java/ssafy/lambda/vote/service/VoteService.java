package ssafy.lambda.vote.service;

import java.util.List;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;

public interface VoteService {

    void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, Long voterId, Long voteeId);

    void review(Long memberId, Long voteInfoId, String review);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);

    List<ResponseVoteDto> getVoteListByMember(Long memberId, Long teamId);

    ResponseVoteStatusDto sortByVoteStatus(Long memberId, List<Long> teamIds);


}
