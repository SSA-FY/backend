package ssafy.lambda.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseProfileWithPercentDto {

    String name;
    String profileImgUrl;
    Double percent;

}
