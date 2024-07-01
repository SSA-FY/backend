package ssafy.lambda.notification.dto;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import ssafy.lambda.global.common.TimeManager;
import ssafy.lambda.notification.dto.item.VoteInfoItem;

@Data
public class ResponseNotificationDto {

    private String dType;
    private String timestamp;

    //vote
    private ResponseVoteNotification voteNotification;

    //expiredVote
    private ResponseExpiredVoteNotification expiredVoteNotification;

    //invitation
    private ResponseInvitionNotification invitionNotification;

    @Builder
    public ResponseNotificationDto(
        String dType,
        ResponseVoteNotification voteNotification,
        ResponseExpiredVoteNotification expiredVoteNotification,
        ResponseInvitionNotification invitionNotification,
        Instant timestamp
    ) {
        this.dType = dType;
        this.voteNotification = voteNotification;
        this.expiredVoteNotification = expiredVoteNotification;
        this.invitionNotification = invitionNotification;
        this.timestamp = TimeManager.timeConverter(timestamp);
    }


    /**
     * 투표알림에 필요한 정보를 채워주는 DTO 생성 메서드
     *
     * @param voteId
     * @param content
     * @param voteInfoItems
     * @return
     */
    public static ResponseNotificationDto NotificationToVoteDto(Long voteId, String content,
        List<VoteInfoItem> voteInfoItems, String dType, Instant timestamp) {
        return ResponseNotificationDto.builder()
                                      .voteNotification(
                                          new ResponseVoteNotification(voteId, content,
                                              voteInfoItems))
                                      .dType(dType)
                                      .timestamp(timestamp)
                                      .build();
    }

    /**
     * 투표 결과에 필요한 정보를 채워주는 DTO 생성 메서드
     *
     * @param expiredVoteId
     * @param content
     * @return
     */
    public static ResponseNotificationDto NotificationToExpiredVoteDto(Long expiredVoteId,
        String content, String dType, Instant timestamp) {
        return ResponseNotificationDto.builder()
                                      .expiredVoteNotification(
                                          new ResponseExpiredVoteNotification(expiredVoteId,
                                              content))
                                      .dType(dType)
                                      .timestamp(timestamp)
                                      .build();

    }

    /**
     * 초대 알림에 필요한 정보를 채워주는 DTO 생성 메서드
     *
     * @param invitationId
     * @param teamName
     * @return
     */
    public static ResponseNotificationDto NotificationToInvitationDto(Long invitationId,
        String teamName, String dType, Instant timestamp) {
        return ResponseNotificationDto.builder()
                                      .invitionNotification(
                                          new ResponseInvitionNotification(invitationId, teamName))
                                      .dType(dType)
                                      .timestamp(timestamp)
                                      .build();
    }

}
