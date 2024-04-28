package ssafy.lambda.vote.repository;

import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.Vote;

public interface VoteRepositoryCustom {
    List<ResponseVoteDto> findVoteByMemberAndTeam(Member member, Team team);

    Vote findInCompleteVoteByMemberAndTeam(Member member, Team team);
}
