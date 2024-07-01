package ssafy.lambda.accesstoken.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.accesstoken.dto.ResponseAccessTokenDto;
import ssafy.lambda.accesstoken.service.AccessTokenService;
import ssafy.lambda.member.dto.ResponseMemberDto;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.service.MemberService;

/**
 * Access Token 발급 Contoller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("token")
public class AccessTokenController {

    private final AccessTokenService accessTokenService;

    /**
     * Access Token 발급
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ResponseEntity
     */
    @Operation(summary = "Access Token 발급", description = "Access Token을 발급 받습니다.")
    @GetMapping
    public ResponseEntity<ResponseAccessTokenDto> create(HttpServletRequest request,
        HttpServletResponse response) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(accessTokenService.createAccessToken(request, response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .build();
        }
    }

    // 개발용
    private final MemberService memberService;

    /**
     * 개발용 Refresh Token 발급
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ResponseEntity
     */
    @Operation(summary = "개발용 Refresh Token 제공", description = "개발용 Refresh Token을 제공 받습니다.")
    @GetMapping("refresh")
    public ResponseEntity<Void> createDev(HttpServletRequest request,
        HttpServletResponse response, @RequestParam String email, @RequestParam String social) {

        System.out.println(email + " "+ social);

        accessTokenService.createRefreshTokenDev(request, response, email, SocialType.valueOf(social));

        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }

    @Operation(summary = "모든 회원 조회 (개발용)", description = "모든 회원을 조회합니다.")
    @GetMapping("member")
    public ResponseEntity<List<ResponseMemberDto>> findAll() {
        List<ResponseMemberDto> memberList = memberService.findAllMember()
                                                          .stream()
                                                          .map(ResponseMemberDto::new)
                                                          .toList();

        return ResponseEntity.ok()
                             .body(memberList);
    }
}
