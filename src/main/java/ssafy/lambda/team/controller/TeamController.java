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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.global.response.Response;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.ResponseTeamDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;

    @Operation(summary = "그룹 목록 조회", description = "그룹 목록을 조회합니다")
    @GetMapping("/list")
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
    public ResponseEntity<Response> createTeam(@RequestBody RequestTeamCreateDto team) {
        // TODO : 요청 사용자는 Spring Security 에서 가져올 것
        Member member = memberService.findMemberById(1L);
        Team createdTeam = teamService.createTeam(team, member);
        return Response.res(HttpStatus.CREATED, "팀 생성 성공");
    }

    @Operation(summary = "그룹 삭제", description = "그룹을 삭제합니다")
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

//    @Operation(summary = "그룹 소개 변경", description = "그룹 소개를 변경합니다.")
//    @PatchMapping
//    public ResponseEntity<Response> updateTeamDescription(@RequestBody
//    RequestTeamDescriptionUpdateDto requestDto) {
//         요청 멤버 확인은 Spring Security 적용시 Principal 에서 가져올 예정입니다.
//        Member member =
//        teamService.updateTeamDescription(requestDto, );
//
//    }
}
