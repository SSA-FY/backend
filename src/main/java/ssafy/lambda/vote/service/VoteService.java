package ssafy.lambda.vote.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;

public interface VoteService {

    void createVote(UUID memberId, Long teamId, RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, UUID voterId, UUID voteeId);

    void review(UUID memberId, Long voteInfoId, String review);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);

    List<ResponseVoteDto> getVoteListByMember(UUID memberId, Long teamId);

    ResponseVoteStatusDto sortByVoteStatus(UUID memberId, List<Long> teamIds);


}
