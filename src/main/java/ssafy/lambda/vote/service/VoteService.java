package ssafy.lambda.vote.service;

import java.util.List;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseCommentDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.dto.ResponseVoteStatusDto;

public interface VoteService {

    void createVote(Long memberId, Long teamId, RequestVoteDto requestVoteDto);

    void doVote(Long voteId, Long teamId, Long memberId, Long choosedMemberId);

    void review(Long memberId, Long voteId, String review);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);

    List<ResponseVoteDto> getUserVote(Long memberId, Long teamId);

    ResponseVoteStatusDto sortByVoteStatus(Long memberId, List<Long> teamIds);


    List<ResponseCommentDto> getComments(Long expriedVoteId);

    void writeComment(Long voteId, Long memberId, String content);

    void deleteComment(Long commentId);


}
