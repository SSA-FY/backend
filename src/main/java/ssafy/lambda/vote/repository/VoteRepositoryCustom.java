package ssafy.lambda.vote.repository;

import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.ResponseVoteDto;

public interface VoteRepositoryCustom {
    List<ResponseVoteDto> findVoteByMemberIdAndTeamId(Member member, Team team);
}
