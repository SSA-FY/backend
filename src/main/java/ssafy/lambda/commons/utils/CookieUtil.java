package ssafy.lambda.commons.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import org.springframework.util.SerializationUtils;

/**
 * 쿠키 관련 유틸
 */
public class CookieUtil {


    /**
     * 쿠키 추가
     *
     * @param response HttpServletResponse
     * @param name     쿠키 키
     * @param value    쿠키 값
     * @param maxAge   쿠키 수명
     */
    public static void addCookie(HttpServletResponse response, String name, String value,
        int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    /**
     * 쿠키 삭제
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     쿠키 키
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
        String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }


    }

    /**
     * 객체 직렬화
     *
     * @param obj 객체
     * @return 직렬화된 문자열
     */
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(obj));
    }

    /**
     * 객체 역직렬화
     *
     * @param cookie 쿠키
     * @param cls    객체 클래스
     * @return the t
     */
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
            SerializationUtils.deserialize(
                Base64.getUrlDecoder()
                    .decode(cookie.getValue())
            )
        );
    }
}

