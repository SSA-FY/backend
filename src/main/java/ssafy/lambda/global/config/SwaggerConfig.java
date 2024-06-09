package ssafy.lambda.global.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import ssafy.lambda.global.annotation.ApiErrorResponse;
import ssafy.lambda.global.response.ApiError;
import ssafy.lambda.global.response.dto.Response;

/**
 * Swagger 설정 Configuration 입니다. 커스텀 어노테이션을 사용하기 위해 사용하였습니다.
 */

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // 커스텀한 어노테이션을 가져온다.
            ApiErrorResponse apiErrorResponse = handlerMethod.getMethodAnnotation(
                ApiErrorResponse.class);
            if (apiErrorResponse != null) {
                // 어노테이션에 넣은 Enum 배열을 인자로 넘겨준다.
                generateResponse(operation, apiErrorResponse.value());
            }
            return operation;
        };
    }

    private void generateResponse(Operation operation,
        ApiError[] apiError) {
        ApiResponses apiResponses = operation.getResponses();
        Map<Integer, List<ApiError>> responseInfoMap = new HashMap<>();
        // HttpStatus 를 key 로 가지는 map 을 만든다.
        for (ApiError responseInfo : apiError) {
            responseInfoMap.putIfAbsent(responseInfo.getStatus()
                                                    .value(), new ArrayList<>());
            responseInfoMap.get(responseInfo.getStatus()
                                            .value())
                           .add(responseInfo);
        }
        // 각 Enum 별로 Example 을 정의한다.
        responseInfoMap.forEach((key, infoList) -> {
            Content content = new Content();
            MediaType mediaType = new MediaType();
            ApiResponse apiResponse = new ApiResponse();
            infoList.forEach(info -> {
                Example example = new Example();
                example.setSummary(info.getName());
                example.setDescription(info.getDescription());
                example.setValue(new Response(info.getStatus(), info.getMessage()));
                System.out.println(info.getName());
                mediaType.addExamples(String.valueOf(info.getName()), example);
            });
            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);
            apiResponses.addApiResponse(String.valueOf(key), apiResponse);
        });


    }

}
