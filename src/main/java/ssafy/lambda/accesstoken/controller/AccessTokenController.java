package ssafy.lambda.accesstoken.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.accesstoken.service.AccessTokenService;

/**
 * Access Token 발급 Contoller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
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
    public ResponseEntity<String> create(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok(accessTokenService.createAccessToken(request, response));
    }

}