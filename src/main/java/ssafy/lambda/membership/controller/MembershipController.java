package ssafy.lambda.membership.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.membership.dto.ResponseMembershipDto;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.ResponseTeamDto;

@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

    // @Operation(summary = "유저와 그룹 관계 조회 (멤버 기준)", description = "유저와 그룹 관계를 멤버 기준으로 조회합니다")
    // @GetMapping
    // public ResponseEntity<List<ResponseMembershipDto>> getALLMembership() {
    //     List<ResponseMembershipDto> teams = membershipService.findAllMembership()
    //                                                          .stream()
    //                                                          .map(ResponseMembershipDto::new)
    //                                                          .toList();
    //     return ResponseEntity.status(HttpStatus.OK)
    //                          .body(teams);
    // }

    // @Operation(summary = "유저와 그룹 관계 조회 (멤버 기준)", description = "유저와 그룹 관계를 멤버 기준으로 조회합니다")
    // @GetMapping("member")
    // public ResponseEntity<List<ResponseMembershipDto>> getMembershipsByMember(
    //     Authentication authentication) {

    //     UUID memberId = UUID.fromString(authentication.getName());

    //     List<ResponseMembershipDto> teams = membershipService.findMembershipByMemberId(memberId)
    //                                                          .stream()
    //                                                          .map(ResponseMembershipDto::new)
    //                                                          .toList();

    //     return ResponseEntity.status(HttpStatus.OK)
    //                          .body(teams);
    // }

    @Operation(summary = "유저와 그룹 관계 조회 (그룹 기준)", description = "유저와 그룹 관계를 그룹 기준으로 조회합니다")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<ResponseMembershipDto>> getMembershipByTeam(
        @PathVariable("teamId") Long teamId) {
        List<ResponseMembershipDto> members = membershipService.findMembershipByTeamId(teamId)
                                                               .stream()
                                                               .map(ResponseMembershipDto::new)
                                                               .toList();

        return ResponseEntity.status(HttpStatus.OK)
                             .body(members);
    }

    // @Operation(summary = "유저와 그룹 관계 삭제", description = "유저와 그룹 관계를 삭제합니다")
    // @DeleteMapping("{MembershipId}")
    // public ResponseEntity<ResponseTeamDto> deleteMembership(
    //     @PathVariable("MembershipId") Long MembershipId) {
    //     membershipService.deleteMembership(MembershipId);

    //     return ResponseEntity.status(HttpStatus.OK)
    //                          .build();
    // }
}
