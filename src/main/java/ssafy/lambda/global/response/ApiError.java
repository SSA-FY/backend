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
    TeamNotFound(HttpStatus.NOT_FOUND, "팀 찾기 오류", "팀을 찾지 못했습니다. name -> {teamName}",
        "팀을 찾지 못했습니다."),
    DuplicatedTeamName(HttpStatus.CONFLICT, "팀 명 설정 오류", "중복된 팀 명 :  + teamName", "팀명이 중복되었습니다."),
    NotificationNotFound(HttpStatus.NOT_FOUND, "알림 오류", "존재하지 않는 알림입니다.",
        "존재하지 않는 알림입니다."),
    UnauthorizedMember(HttpStatus.FORBIDDEN, "권한 오류", "권한이 존재하지 않습니다",
        "멤버 자신의 데이터가 아닌 다른 멤버의 데이터에 접근하는 경우 발생합니다. (URL로 요청 시 발생 가능)"),
    MembershipNotFound(HttpStatus.NOT_FOUND, "멤버십 오류", "팀 내에 해당 멤버가 존재하지 않습니다",
        "잘못된 id를 입력하거나 멤버가 그룹나가기를 하여, 멤버십이 존재하지 않는 경우입니다"),
    VoteInfoNotFoundException(HttpStatus.NOT_FOUND, "투표 정보 찾기 오류", "투표 정보가 존재하지 않습니다.",
        "투표 정보가 존재하지 않습니다."),
    NotEnoughPointException(HttpStatus.FORBIDDEN, "포인트 부족", "사용하고자 하는 기능에 대한 포인트가 부족합니다",
        "포인트가 부족할 때 사용하려고 할 때 발생하는 예외"),
    VoteNotFoundException(HttpStatus.NOT_FOUND, "투표 찾기 오류", "투표를 찾지 못했습니다",
        "잘못한 id를 입력하거나 만료된 투표입니다.");
  
    private final HttpStatus status;
    private final String name;
    private final String message;
    private final String description;

}
