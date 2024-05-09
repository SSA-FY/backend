package ssafy.lambda.notification.repository;

import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.ResponseExpiredVoteNotification;
import ssafy.lambda.notification.dto.ResponseInvitionNotification;
import ssafy.lambda.notification.dto.ResponseVoteNotification;

import java.util.List;

public interface CustomNotificationRepository  {
    List<ResponseVoteNotification> findVoteNotificationByMember(Member member);
    List<ResponseInvitionNotification> findInvitationNotificationByMember(Member member);
    List<ResponseExpiredVoteNotification> findExpiredVoteNotificationByMember(Member member);
}
