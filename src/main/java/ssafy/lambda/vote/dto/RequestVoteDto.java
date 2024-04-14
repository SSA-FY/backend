package ssafy.lambda.vote.dto;

import lombok.Data;

@Data
public class RequestVoteDto {
    String content;
    String backgroundUrl;
    RequestMemberDto memberDto;
}
