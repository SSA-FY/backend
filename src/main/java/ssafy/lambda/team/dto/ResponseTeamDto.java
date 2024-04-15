package ssafy.lambda.team.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.team.entity.Team;

@Getter
public class ResponseTeamDto {

    private final Long teamId;
    private final String teamName;
    private final String description;
    private final LocalDateTime createdAt;
    private final Integer participants;
    private final List<Membership> memberships;

    public ResponseTeamDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.description = team.getDescription();
        this.createdAt = team.getCreatedAt();
        this.participants = team.getParticipants();
        this.memberships = team.getMemberships();
    }
}
