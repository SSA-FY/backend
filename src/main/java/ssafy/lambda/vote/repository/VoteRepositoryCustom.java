package ssafy.lambda.vote.repository;

import java.util.List;
import ssafy.lambda.vote.dto.ResponseVoteDto;

public interface VoteRepositoryCustom {
    List<ResponseVoteDto> findVoteByMemberIdAndTeamId(Long memberId, Long teamId);
}
