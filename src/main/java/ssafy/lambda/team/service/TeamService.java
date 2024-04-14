package ssafy.lambda.team.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.team.dto.RequestTeamDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.repository.TeamRepository;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public Team createTeam(RequestTeamDto requestTeamDto) {
        Team team = requestTeamDto.toEntity();

        return teamRepository.save(team);
    }

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
            .orElse(null);
    }

    @Transactional
    public Team updateTeam(Long id, RequestTeamDto requestTeamDto) {
        Team team = requestTeamDto.toEntity();

        Team updatedTeam = teamRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        updatedTeam.update(team.getTeamName(), team.getDescription());

        return updatedTeam;
    }

    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

}
