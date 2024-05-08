package ssafy.lambda.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.NotificationDetail.InvitationNotification;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.notification.repository.NotificationRepository;
import ssafy.lambda.vote.entity.Vote;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public void createVoteNotification(Member member, Vote vote){
        VoteNotification voteNotification = VoteNotification.builder()
                                                            .member(member)
                                                            .vote(vote)
                                                            .build();
        notificationRepository.save(voteNotification);
    }

    @Override
    public void createExpiredVoteNotification(Member member, ExpiredVote expiredVote) {

    }

    @Override
    public void createInvitationNotification(Member member, Invitation invitaionId) {
        InvitationNotification invitationNotification = InvitationNotification.builder()
                                                                              .invitation(invitaionId)
                                                                              .member(member)
                                                                              .build();
        notificationRepository.save(invitationNotification);
    }
}
