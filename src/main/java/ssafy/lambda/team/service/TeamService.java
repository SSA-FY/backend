package ssafy.lambda.team.service;


import java.util.List;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamDescriptionUpdateDto;
import ssafy.lambda.team.dto.RequestTeamNameUpdateDto;
import ssafy.lambda.team.entity.Team;

public interface TeamService {

    void createTeam(RequestTeamCreateDto teamCreateDto, Member manager);

    Team findTeamById(Long teamId);

    void deleteTeam(Long teamId);

    List<Team> findAllTeam();

    Team findTeamByName(String teamName);

    List<Team> findTeamByNameLike(String teamName);

    void updateTeamDescription(RequestTeamDescriptionUpdateDto requestTeamDescriptionUpdateDto,
        Member member);

    void updateTeamName(RequestTeamNameUpdateDto requestTeamNameUpdateDto, Member member);

    public List<Team> findAllTeamByMemberId(Long memberId);
}
