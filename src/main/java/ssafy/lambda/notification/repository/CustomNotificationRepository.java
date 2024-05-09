package ssafy.lambda.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.ResponseExpiredVoteNotification;
import ssafy.lambda.notification.dto.ResponseInvitionNotification;
import ssafy.lambda.notification.dto.item.VoteInfoItem;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.vote.entity.Vote;

import java.util.List;

public interface CustomNotificationRepository  {
    Page<Notification> findNotificationByMember(Member member, Pageable pageable);
    List<VoteInfoItem> findVoteNotificationInfoByVoteAndMember(Vote vote, Member votee);
}
