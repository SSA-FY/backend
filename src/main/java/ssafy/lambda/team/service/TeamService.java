package ssafy.lambda.team.service;


import java.util.List;
import ssafy.lambda.team.dto.RequestTeamDto;
import ssafy.lambda.team.entity.Team;

public interface TeamService {

    Team createTeam(RequestTeamDto requestTeamDto);

    Team findTeamById(Long teamId);

    Team updateTeam(Long id, RequestTeamDto requestTeamDto);

    void deleteTeam(Long teamId);

    List<Team> findAllTeam();
}
