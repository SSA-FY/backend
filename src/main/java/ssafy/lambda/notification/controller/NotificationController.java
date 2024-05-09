package ssafy.lambda.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.notification.service.NotificationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    @Operation(description = "알림 리스트 반환", summary = "알림 리스트 반환")
    @GetMapping("/list")
    public ResponseEntity<List<ResponseNotificationDto>> getNotificationList(
            @RequestParam(name = "memberId") Long memberId,
            @RequestParam(name = "page") int page) {
        Member member = memberService.findMemberById(memberId);
        return ResponseEntity.ok(notificationService.getNotificationListByMember(member, PageRequest.of(page, 10)));
    }
}
