package ssafy.lambda.point.dto;

import lombok.Builder;

@Builder
public record ResponseGetPointDto(
    String description,
    Long amount) {

}