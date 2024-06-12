package ssafy.lambda.notification.dto;

import lombok.Builder;
import lombok.Data;
import ssafy.lambda.notification.dto.item.VoteInfoItem;

import java.util.List;

@Data
public class ResponseNotificationDto {
    private String title;

    private String dType;

    //vote
    private ResponseVoteNotification voteNotification;

    //expiredVote
    private ResponseExpiredVoteNotification expiredVoteNotification;

    //invitation
    private ResponseInvitionNotification invitionNotification;

    @Builder
    public ResponseNotificationDto(
            String title,
            String dType,
            ResponseVoteNotification voteNotification,
            ResponseExpiredVoteNotification expiredVoteNotification,
            ResponseInvitionNotification invitionNotification
    ) {
        this.title = title;
        this.dType = dType;
        this.voteNotification = voteNotification;
        this.expiredVoteNotification = expiredVoteNotification;
        this.invitionNotification = invitionNotification;
    }



    /**
     * 투표알림에 필요한 정보를 채워주는 DTO 생성 메서드
     * @param voteId
     * @param content
     * @param voteInfoItems
     * @return
     */
    public static ResponseNotificationDto NotificationToVoteDto(Long voteId, String content, List<VoteInfoItem> voteInfoItems) {
        return ResponseNotificationDto.builder()
                                      .voteNotification(new ResponseVoteNotification(voteId, content, voteInfoItems))
                                      .build();
    }

    /**
     * 투표 결과에 필요한 정보를 채워주는 DTO 생성 메서드
     * @param expiredVoteId
     * @param content
     * @return
     */
    public static ResponseNotificationDto NotificationToExpiredVoteDto(Long expiredVoteId, String content){
        return ResponseNotificationDto.builder()
                                      .expiredVoteNotification(new ResponseExpiredVoteNotification(expiredVoteId, content))
                                      .build();

    }

    /**
     * 초대 알림에 필요한 정보를 채워주는 DTO 생성 메서드
     * @param invitationId
     * @param teamName
     * @return
     */
    public static ResponseNotificationDto NotificationToInvitationDto(Long invitationId, String teamName){
        return ResponseNotificationDto.builder()
                                      .invitionNotification(new ResponseInvitionNotification(invitationId, teamName))
                                      .build();
    }

}
