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
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.exception.UnauthorizedMemberException;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.membership.dto.RequestChangeNicknameDto;
import ssafy.lambda.membership.dto.ResponseMembershipDto;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamManagerChangeDto;
import ssafy.lambda.team.dto.RequestTeamUpdateDto;
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
    private final MembershipService membershipService;

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
    @ApiErrorResponse(ApiError.DuplicatedTeamName)
    @GetMapping("{teamId}")
    public ResponseEntity<ResponseTeamDto> getTeam(@PathVariable("teamId") Long teamId) {
        Team team = teamService.findTeamById(teamId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new ResponseTeamDto(team));
    }

    @Operation(summary = "팀 생성", description = "팀을 생성합니다")
    @ApiErrorResponse({ApiError.DuplicatedTeamName})
    @PostMapping
    public ResponseEntity<Response> createTeam(Authentication authentication,
        @RequestBody RequestTeamCreateDto team) {
        UUID memberId = UUID.fromString(authentication.getName());

        Member member = memberService.findMemberById(memberId);
        System.out.println(member.getName() + " this is id " + memberId);
        teamService.createTeam(team, member);
        return Response.res(HttpStatus.CREATED, "팀 생성 성공");
    }

    @Operation(summary = "팀 삭제", description = "팀을 삭제합니다")
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return Response.res(HttpStatus.OK, "그룹 삭제 성공");
    }

    @Operation(summary = "팀 이름 검색", description = "팀 이름으로 조회합니다.")
    @ApiErrorResponse(ApiError.DuplicatedTeamName)
    @GetMapping
    public ResponseEntity<List<ResponseTeamDto>> getTeamByTeamName(
        @RequestParam("teamName") String teamName) {
        List<ResponseTeamDto> teamList = teamService.findTeamByNameLike(teamName)
                                                    .stream()
                                                    .map(ResponseTeamDto::new)
                                                    .toList();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(teamList);
    }

    @Operation(summary = "팀 정보 변경", description = "팀 정보를 변경합니다.")
    @PatchMapping("")
    public ResponseEntity<Response> updateTeam(Authentication authentication,
        @RequestBody
        RequestTeamUpdateDto requestDto) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        teamService.updateTeam(requestDto, member);
        return Response.res(HttpStatus.OK, "그룹 정보 변경 선공");
    }

    @Operation(summary = "팀내 멤버 리스트", description = "팀에 포함된 멤버 리스트를 반환합니다.")
    @ApiErrorResponse({ApiError.TeamNotFound})
    @GetMapping("/member")
    public ResponseEntity<List<ResponseMembershipDto>> getMember(String teamName) {
        Long teamId = teamService.findTeamByName(teamName)
                                 .getTeamId();
        List<Membership> membershipByTeamId = membershipService.findMembershipByTeamId(teamId);
        List<ResponseMembershipDto> responseMembers = membershipByTeamId.stream()
                                                                        .map(
                                                                            ResponseMembershipDto::new)
                                                                        .toList();
        return ResponseEntity.ok(responseMembers);
    }

    @Operation(summary = "팀 내 닉네임 변경", description = "팀 내 보여지는 닉네임을 변경합니다.")
    @PatchMapping("/nickname")
    public ResponseEntity changeNickname(Authentication authentication,
        RequestChangeNicknameDto nicknameDto) {
        UUID memberId = UUID.fromString(authentication.getName());
        teamService.changeNickname(nicknameDto, memberId);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "팀 관리자 변경", description = "팀의 관리자를 변경합니다.")
    @PatchMapping("/manager")
    public ResponseEntity changerManager(Authentication authentication,
        RequestTeamManagerChangeDto requestTeamManagerChangeDto) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        Team team = teamService.findTeamByName(requestTeamManagerChangeDto.getTeamName());
        Member newManager = memberService.findMemberByTag(
            requestTeamManagerChangeDto.getNewTeamManagerTag());
        // 팀의 관리자만 해당 요청을 보낼 수 있음
        if (!team.getManager()
                 .getMemberId()
                 .equals(memberId)) {
            throw new UnauthorizedMemberException();
        }
        teamService.changeManger(newManager, team);
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "팀 나가기", description = "팀을 나갑니다. 관리자는 1명 이상의 다른 팀원이 있을경우 나갈 수 없습니다.")
    @DeleteMapping()
    public ResponseEntity exitTeam(Authentication authentication, String teamName) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        teamService.exitTeam(teamName, member);
        return ResponseEntity.ok()
                             .build();

    }

}
