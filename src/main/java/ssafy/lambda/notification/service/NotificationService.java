package ssafy.lambda.notification.service;

import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.invitation.entity.Invitation;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.vote.entity.Vote;

public interface NotificationService {
    void createVoteNotification(Member member, Vote vote);

    void createExpiredVoteNotification(Member member, ExpiredVote expiredVote);

    void createInvitationNotification(Member member, Invitation invitation);
}
