package knou.seoul.hanwoori.common.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static knou.seoul.hanwoori.common.SessionConst.LOGIN_MEMBER;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String redirectURL = queryString != null ? requestURI + "?" + queryString : requestURI;
        log.info("인증 체크 인터셉터 실행 {}", redirectURL);

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            response.sendRedirect("/login?redirectURL=" + redirectURL);
            return false;
        }

        return true;
    }
}
