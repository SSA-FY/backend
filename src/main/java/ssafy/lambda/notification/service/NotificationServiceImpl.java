package ssafy.lambda.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.notification.entity.NotificationDetail.ExpiredVoteNotification;
import ssafy.lambda.notification.entity.NotificationDetail.InvitationNotification;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.notification.repository.NotificationRepository;
import ssafy.lambda.vote.entity.Vote;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createVoteNotification(Member member, Vote vote){
        VoteNotification voteNotification = VoteNotification.builder()
                                                            .member(member)
                                                            .vote(vote)
                                                            .build();
        notificationRepository.save(voteNotification);
    }

    @Override
    @Transactional
    public void createExpiredVoteNotification(Member member, ExpiredVote expiredVote) {

    }

    @Override
    @Transactional
    public void createInvitationNotification(Member member, Invitation invitaionId) {
        InvitationNotification invitationNotification = InvitationNotification.builder()
                                                                              .invitation(invitaionId)
                                                                              .member(member)
                                                                              .build();
        notificationRepository.save(invitationNotification);
    }

    /**
     * Transactional 선언 이유 :
     * 페이징으로 가져온 결과에서 형변환 로직에서
     * VoteNotification -> Vote참조 시 같은 트랜잭션 내에서만 참조 가능
     * @param member
     * @param pageable
     * @return
     */
    @Override
    @Transactional
    public List<ResponseNotificationDto> getNotificationListByMember(Member member, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findNotificationByMember(member, pageable);

        //TODO: 각 하위타입에 따라 DTO로 변환해서 반환
        Page<ResponseNotificationDto> notificationDtos = notifications.map(notification -> {
            if (notification instanceof VoteNotification) {
                VoteNotification voteNotification = (VoteNotification) notification;
                return ResponseNotificationDto.NotificationToVoteDto(
                        voteNotification.getVote()
                                        .getId(),
                        voteNotification.getVote()
                                        .getContent(),
                        notificationRepository.findVoteNotificationInfoByVoteAndMember(voteNotification.getVote(), voteNotification.getMember()));
            } else if (notification instanceof ExpiredVoteNotification) {
                return null;
            } else if (notification instanceof InvitationNotification) {
                return null;
            } else {
                return null;
            }
        });

        return notificationDtos.getContent();
    }

}
