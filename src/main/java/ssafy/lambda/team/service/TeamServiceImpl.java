package ssafy.lambda.team.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamDescriptionUpdateDto;
import ssafy.lambda.team.dto.RequestTeamNameUpdateDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.exception.TeamUnauthorizedException;
import ssafy.lambda.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public Team createTeam(RequestTeamCreateDto teamCreateDto, Member manager) {
        Team team = teamCreateDto.toEntity();
        team.setManager(manager);
        return teamRepository.save(team);
    }

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                             .orElseThrow(
                                 () -> new IllegalArgumentException("not found: " + teamId));
    }

    public void deleteTeam(Long teamId) {
        // TODO 관리자 검증 과정 추가
        teamRepository.deleteById(teamId);
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    @Override
    public Team findTeamByName(String teamName) {
        return teamRepository.findByTeamName(teamName)
                             .orElseThrow(
                                 () -> new TeamNotFoundException(teamName)
                             );
    }

    @Override
    public List<Team> findTeamByNameLike(String teamName) {
        List<Team> teams = teamRepository.findByTeamNameLike(teamName);
        return teams;
    }

    @Transactional
    @Override
    public void updateTeamDescription(RequestTeamDescriptionUpdateDto requestDto, Member member) {
        Team team = findTeamById(requestDto.getTeamId());
        if (team.getManager()
                .getMemberId() != member.getMemberId()) {
            throw new TeamUnauthorizedException();
        }
        team.setDescription(requestDto.getDescription());
    }

    @Transactional
    @Override
    public void updateTeamName(RequestTeamNameUpdateDto requestTeamNameUpdateDto, Member member) {
        Team team = findTeamById(requestTeamNameUpdateDto.getTeamId());
        if (team.getManager()
                .getMemberId() != member.getMemberId()) {
            throw new TeamUnauthorizedException();
        }
        team.setTeamName(requestTeamNameUpdateDto.getTeamName());
    }
}
