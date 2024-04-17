package ssafy.lambda.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class ResponseProfileWithPercentDto {

    String name;
    String profileImgUrl;
    Double percent;

}
