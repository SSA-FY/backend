package ssafy.lambda.team.service;

import java.util.List;
import java.util.UUID;
import ssafy.lambda.team.dto.ResponseTeamVoteWhetherDto;

public interface TeamWithVoteService {
    List<ResponseTeamVoteWhetherDto> getTeamByVoteOrderByVoteWhether(UUID memberId);
}
