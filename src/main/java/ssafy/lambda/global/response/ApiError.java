package ssafy.lambda.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 에서 발생할 수 있는 오류를 정의해놓는 Enum Class 입니다. Enum 이름(HttpStatus, 오류 이름, 오류 메세지 예시, 오류에 대한 설명) 으로
 * 생성하면 됩니다.
 */

@Getter
@RequiredArgsConstructor
public enum ApiError {
    ExampleCreated(HttpStatus.NOT_IMPLEMENTED, "예제 오류", "예제를 찾지 못했어요", "예제 설명"),
    MemberNotFound(HttpStatus.NOT_FOUND, "멤버 찾기 오류", "멤버 를 찾지 못했습니다. Id -> {memberId}",
        "멤버를 찾지 못했을때 생기는 오류입니다. id 로 멤버를 검색했지만 찾지 못했을 경우 발생합니다."),
    TeamNotFound(HttpStatus.NOT_FOUND, "팀 찾기 오륲", "팀을 찾지 못했습니다. name -> {teamName}",
        "팀을 찾지 못했습니다."),
    ;

    private final HttpStatus status;
    private final String name;
    private final String message;
    private final String description;

}
