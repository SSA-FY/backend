package ssafy.lambda.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.global.response.dto.ResponseData;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.notification.service.NotificationService;

import java.util.List;
import java.util.Locale;
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
    @ApiErrorResponse({ApiError.MemberNotFound})
    @GetMapping("/list")
    public ResponseEntity<List<ResponseNotificationDto>> getNotificationList(
            Authentication authentication,
            @RequestParam(name = "page") int page) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        return ResponseData.res(HttpStatus.OK, "알림 리스트", notificationService.getNotificationListByMember(member, PageRequest.of(page, 10)));
    }

    @Operation(description = "해당 알림을 삭제합니다.", summary = "알림 삭제")
    @ApiErrorResponse({ApiError.NotificationNotFound, ApiError.UnauthorizedMember})
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<HttpStatus> deleteNotification(
            Authentication authentication,
            @PathVariable("notificationId") Long notificationId) {
        UUID memberId = UUID.fromString(authentication.getName());
        Member member = memberService.findMemberById(memberId);
        if (notificationService.deleteNotification(member, notificationId)) {
            return Response.res(HttpStatus.NO_CONTENT, "알림을 삭제하였습니다.");
        }
        return Response.res(HttpStatus.NOT_FOUND, "존재하지 않는 알림입니다.");
    }
}
