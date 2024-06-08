package ssafy.lambda.invitation.controller;

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
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.invitation.dto.RequestAcceptInvitationDto;
import ssafy.lambda.invitation.dto.RequestCreateInvitationDto;
import ssafy.lambda.invitation.dto.ResponseInvitationDto;
import ssafy.lambda.invitation.service.InvitationService;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.team.service.TeamService;

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
        return ResponseEntity.ok(invitationService.getAllInvitations()
                                                  .stream()
                                                  .map(
                                                      (invitation -> new ResponseInvitationDto(
                                                          invitation))
                                                  )
                                                  .toList());

    }

    @PostMapping
    public ResponseEntity<Response> createInvitation(
        @RequestBody RequestCreateInvitationDto requestCreateInvitationDto) {
        //TODO security 로 MemberID 가져와서 해당 팀에 초대권한이 있는 사람인지 체크
        invitationService.createInvitation(requestCreateInvitationDto.getMemberId(),
            requestCreateInvitationDto.getTeamId());
        return Response.res(HttpStatus.CREATED, "초대가 생성되었습니다.");
    }

    @PostMapping("/accept")
    public ResponseEntity<Response> acceptInvitation(@RequestBody
    RequestAcceptInvitationDto requestAcceptInvitation) {
        invitationService.acceptInvitation(requestAcceptInvitation);
        return Response.res(HttpStatus.OK, "초대를 수락하였습니다.");
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Response> rejectInvitation(
        @PathVariable(name = "invitationId") Long invitationId) {
        invitationService.rejectInvitation(invitationId);
        return Response.res(HttpStatus.OK, "초대를 삭제하였습니다.");
    }
}
