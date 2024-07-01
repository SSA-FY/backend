package ssafy.lambda.team.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.ResponseTeamVoteWhetherDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.vote.service.VoteService;

@Service
@RequiredArgsConstructor
public class TeamWithVoteServiceImpl implements TeamWithVoteService {

    private final MembershipService membershipService;
    private final VoteService voteService;
    private final TeamService teamService;

    @Override
    public List<ResponseTeamVoteWhetherDto> getTeamByVoteOrderByVoteWhether(UUID memberId) {
        //멤버가 속한 모든 팀 반환
        List<Team> teamList = teamService.findAllTeamByMemberId(memberId);

        //정렬된 해야할 투표가 남아있는 팀 리스트를 반환
        List<Team> inCompleteTeamList = voteService.sortedTeamByVoteWhether(memberId,
            teamList);

        List<ResponseTeamVoteWhetherDto> sortedTeamDtoList = new ArrayList<>();
        Map<Long, Boolean> map = new HashMap<>();
        for (Team team : inCompleteTeamList) {
            sortedTeamDtoList.add(ResponseTeamVoteWhetherDto.teamToDto(team, true));
            map.put(team.getTeamId(), true);
        }

        for (Team team : teamList) {
            if (!map.getOrDefault(team.getTeamId(), false)) {
                sortedTeamDtoList.add(ResponseTeamVoteWhetherDto.teamToDto(team, false));
                map.put(team.getTeamId(), true);
            }
        }
        return sortedTeamDtoList;
    }
}
