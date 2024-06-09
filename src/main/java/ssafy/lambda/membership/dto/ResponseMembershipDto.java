package ssafy.lambda.membership.dto;

import java.util.UUID;
import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;

@Getter
public class ResponseMembershipDto {

    private final Long id;
    private final UUID memberId;
    private final Long teamId;
    private final String nickname;

    public ResponseMembershipDto(Membership membership) {
        this.id = membership.getMembershipId();
        this.memberId = membership.getMember()
                                  .getMemberId();
        this.teamId = membership.getTeam()
                                .getTeamId();
        this.nickname = membership.getNickname();

    }
}
