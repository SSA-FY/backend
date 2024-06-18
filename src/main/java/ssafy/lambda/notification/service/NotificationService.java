package ssafy.lambda.notification.service;

import org.springframework.data.domain.Pageable;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.vote.entity.Vote;

import java.util.List;
import java.util.Locale;

public interface NotificationService {
    void createVoteNotification(Member member, Vote vote);

    void createExpiredVoteNotification(Member member, ExpiredVote expiredVote);

    void createInvitationNotification(Member member, Invitation invitation);

    List<ResponseNotificationDto> getNotificationListByMember(Member member, Pageable pageable);

    boolean deleteNotification(Member member, Long notificationId);
}
