package ssafy.lambda.accesstoken.controller;

import static ssafy.lambda.config.security.jwt.JwtProperties.HEADER_AUTHORIZATION;
import static ssafy.lambda.config.security.jwt.JwtProperties.TOKEN_PREFIX;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.accesstoken.service.AccessTokenService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class AccessTokenController {

    private final AccessTokenService accessTokenService;

    @Operation(summary = "Access Token 발급", description = "Access Token을 발급 받습니다.")
    @GetMapping
    public ResponseEntity<String> create(HttpServletResponse httpServletResponse,
        @RequestHeader(name = HEADER_AUTHORIZATION) String authorizationHeader) {

        String token = getAccessToken(authorizationHeader);

        return ResponseEntity.status(HttpStatus.OK)
            .body(accessTokenService.createAccessToken(httpServletResponse, token));
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
