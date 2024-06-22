package ssafy.lambda.team.dto;

import lombok.Builder;
import lombok.Data;
import ssafy.lambda.team.entity.Team;

@Data
public class ResponseTeamVoteWhetherDto {
    private Long teamId;
    private String teamName;
    private Boolean voteWhether;

    @Builder
    public ResponseTeamVoteWhetherDto(Long teamId, String teamName, Boolean voteWhether) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.voteWhether = voteWhether;
    }

    public static ResponseTeamVoteWhetherDto teamToDto(Team team, boolean voteWhether) {
        return ResponseTeamVoteWhetherDto.builder()
                                         .teamId(team.getTeamId())
                                         .teamName(team.getTeamName())
                                         .voteWhether(voteWhether)
                                         .build();
    }
}
