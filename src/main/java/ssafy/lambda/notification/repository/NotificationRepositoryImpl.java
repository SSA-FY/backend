package ssafy.lambda.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.QResponseVoteNotification;
import ssafy.lambda.notification.dto.ResponseExpiredVoteNotification;
import ssafy.lambda.notification.dto.ResponseInvitionNotification;
import ssafy.lambda.notification.dto.ResponseVoteNotification;
import ssafy.lambda.notification.dto.item.QVoteInfoItem;
import ssafy.lambda.notification.dto.item.VoteInfoItem;

import java.util.List;

import static ssafy.lambda.notification.entity.NotificationDetail.QVoteNotification.voteNotification;
import static ssafy.lambda.vote.entity.QVote.vote;
import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

public class NotificationRepositoryImpl implements CustomNotificationRepository {

    private final JPAQueryFactory queryFactory;

    public NotificationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    /**
     * 투표 받은 정보에 대한 알림 반환 메서드
     * @param member
     * @return
     */
    @Override
    public List<ResponseVoteNotification> findVoteNotificationByMember(Member member) {
        List<ResponseVoteNotification> votenotifications = queryFactory.select(new QResponseVoteNotification(vote.id, vote.content))
                                                                       .from(voteNotification)
                                                                       .join(vote)
                                                                       .on(voteNotification.vote.eq(vote))
                                                                       .where(voteNotification.member.eq(member))
                                                                       .orderBy(voteNotification.updatedAt.asc())
                                                                       .fetch();
        votenotifications.forEach((voteNotificationItem) -> voteNotificationItem.setVoteInfoItems(findVoteInfoItemByVoteId(voteNotificationItem.getVoteId())));
        return votenotifications;
    }

    /**
     * 투표 받은 알림에 대한 voteInfo 정보 반환 메서드
     * 정렬 : 공개 여부 우선, 한줄 평 길이 우선 null Last
     * @param voteId
     * @return
     */
    public List<VoteInfoItem> findVoteInfoItemByVoteId(Long voteId){
        return queryFactory.select(new QVoteInfoItem(voteInfo.id, voteInfo.opinion, voteInfo.isOpen))
                           .from(voteInfo)
                           .where(voteInfo.vote.id.eq(voteId))
                           .orderBy(voteInfo.isOpen.asc(), voteInfo.opinion.length()
                                                                           .desc()
                                                                           .nullsLast())
                           .fetch();
    }

    @Override
    public List<ResponseInvitionNotification> findInvitationNotificationByMember(Member member) {
        return null;
    }

    @Override
    public List<ResponseExpiredVoteNotification> findExpiredVoteNotificationByMember(Member member) {
        return null;
    }
}
