package ssafy.lambda.team.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;

@AllArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<Team> getTeams() {
        return teamService.findAllTeam();
    }

    @GetMapping("{id}")
    public Team getTeam(@PathVariable("id") Long id) {
        return teamService.findTeamById(id);
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping
    public Team updateTeam(@RequestBody Team team) {
        return teamService.updateTeam(team);
    }

    @DeleteMapping("{id}")
    public void deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
    }
}
