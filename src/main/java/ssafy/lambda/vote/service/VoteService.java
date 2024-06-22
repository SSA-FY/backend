package ssafy.lambda.vote.service;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.RequestReviewDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;

public interface VoteService {

    void createVote(UUID memberId, Long teamId, RequestVoteDto requestVoteDto, MultipartFile img);

    Long doVote(Long voteId, UUID voterId, UUID voteeId);

    void review(UUID memberId, Long voteInfoId, RequestReviewDto requestReviewDto);

    List<ResponseProfileWithPercentDto> voteResult(Long voteId);

    List<ResponseVoteDto> getVoteListByMember(UUID memberId, Long teamId);

    List<Team> sortedTeamByVoteWhether(UUID memberId, List<Team> teamList);
}
