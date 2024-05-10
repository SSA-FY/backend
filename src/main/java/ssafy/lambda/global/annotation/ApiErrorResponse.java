package ssafy.lambda.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ssafy.lambda.global.response.ApiError;

/**
 * Controller 에 붙여서 사용하는 API 에서 생길수 있는 에러들을 쉽게 정의할 수 있는 커스텀 어노테이션 입니다.
 *
 * @ApiErrorResponse({)) => 배열 안에 ApiErrorResponse Enum 을 넣으면 작동합니다.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponse {

    ApiError[] value();
}
