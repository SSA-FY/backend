package ssafy.lambda.vote.repository;

import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.dto.ResponseVoteDto;

public interface VoteRepositoryCustom {

    List<ResponseVoteDto> findVoteByVoterAndTeam(Member voter, Team team);

    List<Object[]> findVoteInfoByCnt(Long voteId);

    List<Team> findByMemberAndVoteWhether(Member member, List<Team> teamList);

    Long deleteVoteByExpiredVote(List<Long> voteIds);
}
