package ssafy.lambda.notification.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.dto.item.QVoteInfoItem;
import ssafy.lambda.notification.dto.item.VoteInfoItem;
import ssafy.lambda.notification.entity.Notification;
import ssafy.lambda.notification.entity.NotificationDetail.VoteNotification;
import ssafy.lambda.vote.entity.Vote;

import java.util.List;

import static ssafy.lambda.board.entity.QExpiredVoteInfo.expiredVoteInfo;
import static ssafy.lambda.notification.entity.NotificationDetail.QVoteNotification.voteNotification;
import static ssafy.lambda.notification.entity.QNotification.notification;
import static ssafy.lambda.vote.entity.QVoteInfo.voteInfo;

public class NotificationRepositoryImpl implements CustomNotificationRepository {

    private final JPAQueryFactory queryFactory;

    public NotificationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 페이징
     *
     * @param member
     * @return
     */
    @Override
    public Page<Notification> findNotificationByMember(Member member, Pageable pageable) {
        List<Notification> notifications = queryFactory.selectFrom(notification)
                                                       .where(notification.member.eq(member))
                                                       .offset(pageable.getOffset())
                                                       .limit(pageable.getPageSize())
                                                       .fetch();

        JPAQuery<Long> count = queryFactory.select(notification.count())
                                          .from(notification);

        return PageableExecutionUtils.getPage(notifications, pageable, count::fetchOne);
    }

    /**
     * 투표 받은 알림에 대한 voteInfo 정보 반환 메서드
     * 정렬 : 공개 여부 우선, 한줄 평 길이 우선 null Last
     * @param
     * @return
     */
    @Override
    public List<VoteInfoItem> findVoteNotificationInfoByVoteAndMember(Vote vote, Member votee) {
        return queryFactory.select(new QVoteInfoItem(voteInfo.id, voteInfo.opinion, voteInfo.isOpen))
                           .from(voteInfo)
                           .where(voteInfo.vote.eq(vote), voteInfo.votee.eq(votee))
                           .orderBy(voteInfo.isOpen.asc(), voteInfo.opinion.length()
                                                                           .desc()
                                                                           .nullsLast())
                           .fetch();
    }
    @Override
    public List<VoteInfoItem> findVoteNotificationInfoByVoteAndMember(ExpiredVote expiredVote, Member votee) {
        return queryFactory.select(new QVoteInfoItem(expiredVoteInfo.id, expiredVoteInfo.opinion, expiredVoteInfo.isOpen))
                           .from(expiredVoteInfo)
                           .where(expiredVoteInfo.expiredVote.eq(expiredVote), expiredVoteInfo.votee.eq(votee))
                           .orderBy(expiredVoteInfo.isOpen.asc(), expiredVoteInfo.opinion.length()
                                                                           .desc()
                                                                           .nullsLast())
                           .fetch();
    }

    @Override
    public VoteNotification findNotificationByVoteId(Long voteId) {
        return queryFactory.select(voteNotification)
                           .from(voteNotification)
                           .where(voteNotification.vote.id.eq(voteId))
                           .fetchOne();
    }
}
