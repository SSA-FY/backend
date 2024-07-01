package ssafy.lambda.notification.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.global.exception.UnauthorizedMemberException;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.ResponseNotificationDto;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.notification.entity.NotificationDetail.ExpiredVoteNotification;
import ssafy.lambda.notification.entity.NotificationDetail.InvitationNotification;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.notification.exception.NotificationNotFoundException;
import ssafy.lambda.notification.repository.NotificationRepository;
import ssafy.lambda.vote.entity.Vote;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createVoteNotification(Member member, Vote vote) {
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
                                                                              .invitation(
                                                                                  invitaionId)
                                                                              .member(member)
                                                                              .build();
        notificationRepository.save(invitationNotification);
    }

    /**
     * Transactional 선언 이유 : 페이징으로 가져온 결과에서 형변환 로직에서 VoteNotification -> Vote참조 시 같은 트랜잭션 내에서만 참조
     * 가능
     *
     * @param member
     * @param pageable
     * @return
     */
    @Override
    @Transactional
    public List<ResponseNotificationDto> getNotificationListByMember(Member member,
        Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findNotificationByMember(member,
            pageable);

        //TODO: 각 하위타입에 따라 DTO로 변환해서 반환
        Page<ResponseNotificationDto> notificationDtos = notifications.map(notification -> {
            if (notification instanceof VoteNotification) {
                VoteNotification voteNotification = (VoteNotification) notification;
                return ResponseNotificationDto.NotificationToVoteDto(
                    voteNotification.getVote()
                                    .getId(),
                    voteNotification.getVote()
                                    .getContent(),
                    notificationRepository.findVoteNotificationInfoByVoteAndMember(
                        voteNotification.getVote(), voteNotification.getMember()),
                    "Vote",
                    voteNotification.getCreatedAt()
                );
            } else if (notification instanceof ExpiredVoteNotification) {
                ExpiredVoteNotification expiredVoteNotification = (ExpiredVoteNotification) notification;
                return ResponseNotificationDto.NotificationToExpiredVoteDto(
                    expiredVoteNotification.getExpiredVote()
                                           .getId(),
                    expiredVoteNotification.getExpiredVote()
                                           .getContent(),
                    "ExpiredVote",
                    expiredVoteNotification.getCreatedAt()
                );
            } else {
                InvitationNotification invitationNotification = (InvitationNotification) notification;
                return ResponseNotificationDto.NotificationToInvitationDto(
                    invitationNotification.getInvitation()
                                          .getId(),
                    invitationNotification.getInvitation()
                                          .getTeam()
                                          .getTeamName(),
                    "Invitation",
                    invitationNotification.getCreatedAt()
                );
            }
        });

        return notificationDtos.getContent();
    }

    /**
     * 요청한 멤버에 대한 알림이 맞는 경우만 삭제하는 서비스
     *
     * @param member
     * @param notificationId
     * @return
     */
    @Override
    public boolean deleteNotification(Member member, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                                                          .orElseThrow(
                                                              NotificationNotFoundException::new);
        Member notiMember = notification.getMember();

        if (!notiMember.equals(member)) {
            throw new UnauthorizedMemberException();
        }
        notificationRepository.delete(notification);
        return true;
    }

}
