package ssafy.lambda.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.notification.service.NotificationService;

import java.util.List;
import java.util.UUID;

@Slf4j
@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {


    private final NotificationService notificationService;
    private final MemberService memberService;

    @Operation(description = "알림 리스트 반환", summary = "알림 리스트 반환")
    @GetMapping("/list")
    public ResponseEntity<List<ResponseNotificationDto>> getNotificationList(
            Authentication authentication,
            @RequestParam(name = "page") int page) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        return ResponseEntity.ok(notificationService.getNotificationListByMember(member, PageRequest.of(page, 10)));
    }
}
