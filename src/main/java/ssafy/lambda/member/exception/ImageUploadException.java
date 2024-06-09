package ssafy.lambda.member.exception;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException() {
        super("이미지 업로드에 실패하였습니다.");
    }

}
