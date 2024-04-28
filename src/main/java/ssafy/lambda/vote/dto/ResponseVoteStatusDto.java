package ssafy.lambda.vote.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 투표상태에 따라 2개의 리스트로 분리하여 반환
 * inComplatedTeam
 */
@Data
public class ResponseVoteStatusDto {

    //TeamId를 반환하도록 <Long> 타입으로 설정
    List<Long> completedTeams = new ArrayList<>();
    List<Long> inCompletedTeams = new ArrayList<>();
}
