package ssafy.lambda.membership.controller;

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
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.membership.dto.RequestMembershipDto;
import ssafy.lambda.membership.dto.ResponseMembershipDto;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.ResponseTeamDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

    @Operation(summary = "유저와 그룹 관계 조회 (멤버 기준)", description = "유저와 그룹 관계를 멤버 기준으로 조회합니다")
    @GetMapping("/member/{id}")
    public ResponseEntity<List<ResponseMembershipDto>> getMembershipsByMember(
        @PathVariable("id") Long id) {

        List<ResponseMembershipDto> teams = membershipService.findMembershipByMember(id)
            .stream()
            .map(ResponseMembershipDto::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(teams);
    }

    @Operation(summary = "유저와 그룹 관계 조회 (그룹 기준)", description = "유저와 그룹 관계를 그룹 기준으로 조회합니다")
    @GetMapping("/team/{id}")
    public ResponseEntity<List<ResponseMembershipDto>> getMembershipByTeam(
        @PathVariable("id") Long id) {
        List<ResponseMembershipDto> members = membershipService.findMembershipByTeam(id)
            .stream()
            .map(ResponseMembershipDto::new)
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(members);
    }

    @Operation(summary = "유저와 그룹 관계 생성", description = "유저와 그룹 관계를 생성합니다")
    @PostMapping
    public ResponseEntity<ResponseMembershipDto> createMembership(
        @RequestBody RequestMembershipDto requestMembershipDto) {
        Membership membership = membershipService.createMembership(requestMembershipDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMembershipDto(membership));
    }

    @Operation(summary = "유저와 그룹 관계 삭제", description = "유저와 그룹 관계를 삭제합니다")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseTeamDto> deleteMembership(@PathVariable("id") Long id) {
        membershipService.deleteMembership(id);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }
}
