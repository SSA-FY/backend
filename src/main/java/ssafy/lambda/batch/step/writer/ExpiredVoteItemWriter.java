package ssafy.lambda.batch.step.writer;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.board.repository.ExpiredVoteRepository;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.repository.MembershipRepository;
import ssafy.lambda.notification.entity.NotificationDetail.ExpiredVoteNotification;
import ssafy.lambda.notification.repository.NotificationRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Component
@RequiredArgsConstructor
public class ExpiredVoteItemWriter implements ItemWriter<ExpiredVote> {

    private final ExpiredVoteRepository expiredVoteRepository;
    private final VoteRepository voteRepository;

    private final MembershipRepository membershipRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void write(Chunk<? extends ExpiredVote> chunk) throws Exception {
        List<Long> voteIds = new ArrayList<>();
        for (ExpiredVote expiredVote : chunk) {
            voteIds.add(expiredVote.getVoteId());
            expiredVoteRepository.save(expiredVote);

            List<Membership> memberships = membershipRepository.findByTeam(expiredVote.getTeam());
            for (Membership membership : memberships) {
                ExpiredVoteNotification expiredVoteNotification = ExpiredVoteNotification.builder()
                                                                                         .member(membership.getMember())
                                                                                         .expiredVote(expiredVote)
                                                                                         .build();
                notificationRepository.save(expiredVoteNotification);
            }
        }
        voteRepository.deleteVoteByExpiredVote(voteIds);
    }
}
