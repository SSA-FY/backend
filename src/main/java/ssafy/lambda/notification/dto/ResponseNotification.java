package ssafy.lambda.notification.dto;

public abstract class ResponseNotification {

    //알림 제목
    private String title;

    //알림 내용
    /**
     * 1. 투표 : 누군가 나를 뽑았어요
     * 2. 초대 : 그룹에 초대되었어요
     * 3. 결과 : 투표 결과가 나왔어요
     */
    private String content;

    public ResponseNotification() {
    }

    public ResponseNotification(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
