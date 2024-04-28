package ssafy.lambda.config.security.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;
import ssafy.lambda.commons.utils.CookieUtil;

/**
 * Authorization Code를 이용한 oauth2 방식에서 요청 정보를 저장하는 Repository
 */
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // 쿠키 키
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    // 쿠키 수명
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    /**
     * 쿠키에서 추출한 요청 정보 반환
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return oauth2 요청 정보
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
        HttpServletResponse response) {
        return loadAuthorizationRequest(request);
    }

    /**
     * 쿠키에서 요청 정보 추출
     *
     * @param request HttpServletRequest
     * @return oauth2 요청 정보
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    /**
     * 쿠키에 요청 정보 저장
     *
     * @param authorizationRequest 요청 정보
     * @param request              HttpServletRequest
     * @param response             HttpServletResponse
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
        HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    }

    /**
     * 쿠키에서 요청 정보 삭제
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void removeAuthorizationRequestCookies(HttpServletRequest request,
        HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
