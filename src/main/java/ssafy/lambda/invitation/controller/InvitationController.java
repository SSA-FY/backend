package ssafy.lambda.invitation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.global.exception.UnauthorizedMemberException;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.invitation.dto.RequestAcceptInvitationDto;
import ssafy.lambda.invitation.dto.ResponseInvitationDto;
import ssafy.lambda.invitation.service.InvitationService;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.team.service.TeamService;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("invitation")
public class InvitationController {

    private final InvitationService invitationService;
    private final MemberService memberService;
    private final TeamService teamService;


    /**
     * 실행 확인을 위한 개발 단계에서만 사용하는 API 생성된 초대 목록을 모두 반환한다.
     */
    @GetMapping
    public ResponseEntity<List<ResponseInvitationDto>> getInvitations() {
        return ResponseEntity.ok()
                             .body(invitationService.getAllInvitations()
                                                    .stream()
                                                    .map(
                                                        (invitation -> new ResponseInvitationDto(
                                                            invitation))
                                                    )
                                                    .toList());

    }

    @Operation(summary = "멤버 초대", description = "팀에 멤버를 추가합니다")
    @PostMapping
    public ResponseEntity<Response> createInvitation(
        Authentication authentication,
        @RequestParam(name = "teamName") String teamName,
        @RequestBody List<String> memberList
    ) {
        // 팀 검색
        Long teamId = teamService.findTeamByName(teamName)
                                 .getTeamId();

        UUID memberId = UUID.fromString(authentication.getName());
        // 초대권한이 있는 사람(매니저)인지 체크
        if (!teamService.findTeamById(teamId)
                        .getManager()
                        .getMemberId()
                        .equals(memberId)) {
            throw new UnauthorizedMemberException();
        }
        memberList
            .forEach((mId) -> {
                invitationService.createInvitation(UUID.fromString(mId),
                    teamId);
            });
        return ResponseEntity.ok()
                             .build();
    }

    @Operation(summary = "초대 수락", description = "초대를 수락하고 팀에 가입합니다")
    @PostMapping("/accept")
    public ResponseEntity<Response> acceptInvitation(@RequestBody
    RequestAcceptInvitationDto requestAcceptInvitation) {
        invitationService.acceptInvitation(requestAcceptInvitation);
        return ResponseEntity.ok()
                             .build();
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Response> rejectInvitation(
        @PathVariable(name = "invitationId") Long invitationId) {
        invitationService.rejectInvitation(invitationId);
        return ResponseEntity.ok()
                             .build();
    }
}
