package ssafy.lambda.team.service;


import java.util.List;
import java.util.UUID;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.dto.RequestChangeNicknameDto;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamUpdateDto;
import ssafy.lambda.team.entity.Team;

public interface TeamService {

    void createTeam(RequestTeamCreateDto teamCreateDto, Member manager);

    Team findTeamById(Long teamId);

    void deleteTeam(Long teamId);

    List<Team> findAllTeam();

    Team findTeamByName(String teamName);

    List<Team> findTeamByNameLike(String teamName);

    public List<Team> findAllTeamByMemberId(UUID memberId);

    void updateTeam(RequestTeamUpdateDto requestTeamUpdateDto, Member member);

    void changeNickname(RequestChangeNicknameDto requestChangeNicknameDto, UUID memberId);
}
