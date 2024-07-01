package ssafy.lambda.team.dto;

import lombok.Builder;
import lombok.Data;
import ssafy.lambda.team.entity.Team;

@Data
public class ResponseTeamVoteWhetherDto {

    private String teamName;
    private String imgUrl;
    private Boolean voteWhether;

    @Builder
    public ResponseTeamVoteWhetherDto(String teamName, Boolean voteWhether, String imgUrl) {
        this.teamName = teamName;
        this.voteWhether = voteWhether;
        this.imgUrl = imgUrl;
    }

    public static ResponseTeamVoteWhetherDto teamToDto(Team team, boolean voteWhether) {
        return ResponseTeamVoteWhetherDto.builder()
                                         .teamName(team.getTeamName())
                                         .voteWhether(voteWhether)
                                         .imgUrl(team.getImgUrl())
                                         .build();
    }
}
