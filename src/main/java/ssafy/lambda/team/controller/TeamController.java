package ssafy.lambda.team.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamDescriptionUpdateDto;
import ssafy.lambda.team.dto.RequestTeamNameUpdateDto;
import ssafy.lambda.team.dto.ResponseTeamDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;

    @Operation(summary = "팀 목록 조회", description = "팀 목록을 조회합니다")
    @GetMapping("/list")
    public ResponseEntity<List<ResponseTeamDto>> getTeams() {
        List<ResponseTeamDto> teams = teamService.findAllTeam()
                                                 .stream()
                                                 .map(ResponseTeamDto::new)
                                                 .toList();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(teams);
    }

    @Operation(summary = "팀 조회", description = "팀을 조회합니다")
    @GetMapping("{teamId}")
    public ResponseEntity<ResponseTeamDto> getTeam(@PathVariable("teamId") Long teamId) {
        Team team = teamService.findTeamById(teamId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseTeamDto(team));
    }

    @Operation(summary = "그룹 생성", description = "그룹을 생성합니다")
    @PostMapping
    public ResponseEntity<Response> createTeam(Authentication authentication,
        @RequestBody RequestTeamCreateDto team) {
        UUID memberId = UUID.fromString(authentication.getName());

        Member member = memberService.findMemberById(memberId);
        teamService.createTeam(team, member);
        return Response.res(HttpStatus.CREATED, "팀 생성 성공");
    }

    @Operation(summary = "팀 삭제", description = "팀을 삭제합니다")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return Response.res(HttpStatus.OK, "그룹 삭제 성공");
    }

    @Operation(summary = "그룹 이름 검색", description = "그룹 이름으로 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ResponseTeamDto>> getTeamByTeamName(
        @RequestParam("teamName") String teamName) {
        List<ResponseTeamDto> teamList = teamService.findTeamByNameLike(teamName)
                                                    .stream()
                                                    .map((team -> new ResponseTeamDto(team)))
                                                    .toList();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(teamList);
    }

    @Operation(summary = "팀 소개 변경", description = "팀 소개를 변경합니다.")
    @PatchMapping("/description")
    public ResponseEntity<Response> updateTeamDescription(Authentication authentication,
        @RequestBody
        RequestTeamDescriptionUpdateDto requestDto) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        teamService.updateTeamDescription(requestDto, member);
        return Response.res(HttpStatus.OK, "그룹 소개 변경 선공");
    }

    @Operation(summary = "팀명 변경", description = "팀명을 변경합니다.")
    @PatchMapping("name")
    public ResponseEntity<Response> updateTeamName(Authentication authentication,
        @RequestBody RequestTeamNameUpdateDto requestDto) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        teamService.updateTeamName(requestDto, member);
        return Response.res(HttpStatus.OK, "팀명 변경 성공");
    }


}
