package ssafy.lambda.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.point.dto.ResponseGetPointDto;
import ssafy.lambda.point.service.PointService;

/**
 * Access Token 발급 Contoller
 */
@SecurityRequirement(name = "token")
@RequiredArgsConstructor
@RestController
@RequestMapping("point")
public class PointController {

    private final PointService pointService;

    /**
     * Access Token 발급
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ResponseEntity
     */
    @Operation(summary = "Access Token 발급", description = "Access Token을 발급 받습니다.")
    @GetMapping
    public ResponseEntity<Map<String, List<ResponseGetPointDto>>> getPoint(
        Authentication authentication) {
        UUID memberId = UUID.fromString(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(pointService.findAllByMemberId(memberId));
    }
}
