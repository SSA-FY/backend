package ssafy.lambda.team.service;


import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.dto.RequestChangeNicknameDto;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamUpdateDto;
import ssafy.lambda.team.entity.Team;

public interface TeamService {

    Long createTeam(Member member, RequestTeamCreateDto teamCreateDto, MultipartFile img);

    Team findTeamById(Long teamId);

    void deleteTeam(Long teamId);

    List<Team> findAllTeam();

    Team findTeamByName(String teamName);

    List<Team> findTeamByNameLike(String teamName);

    public List<Team> findAllTeamByMemberId(UUID memberId);

    void updateTeam(Member member, Member newManager, RequestTeamUpdateDto requestTeamUpdateDto,
        MultipartFile img);

    void changeNickname(RequestChangeNicknameDto requestChangeNicknameDto, UUID memberId);

    void changeManger(Member newManager, Team team);

    void exitTeam(String teamName, Member member);
}
