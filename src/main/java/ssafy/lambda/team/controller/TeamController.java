package ssafy.lambda.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.team.dto.RequestTeamDto;
import ssafy.lambda.team.dto.ResponseTeamDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "그룹 목록 조회", description = "그룹 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<ResponseTeamDto>> getTeams() {
        List<ResponseTeamDto> teams = teamService.findAllTeam()
            .stream()
            .map(ResponseTeamDto::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(teams);
    }

    @Operation(summary = "그룹 조회", description = "그룹을 조회합니다")
    @GetMapping("{id}")
    public ResponseEntity<ResponseTeamDto> getTeam(@PathVariable("id") Long id) {
        Team team = teamService.findTeamById(id);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseTeamDto(team));
    }

    @Operation(summary = "그룹 생성", description = "그룹을 생성합니다")
    @PostMapping
    public ResponseEntity<ResponseTeamDto> createTeam(@RequestBody RequestTeamDto team) {
        Team createdTeam = teamService.createTeam(team);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseTeamDto(createdTeam));
    }

    @Operation(summary = "그룹 갱신", description = "그룹을 갱신합니다")
    @PutMapping("{id}")
    public ResponseEntity<ResponseTeamDto> updateTeam(@PathVariable("id") Long id,
        @RequestBody RequestTeamDto team) {
        Team updatedTeam = teamService.updateTeam(id, team);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseTeamDto(updatedTeam));
    }

    @Operation(summary = "그룹 삭제", description = "그룹을 삭제합니다")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseTeamDto> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }
}
