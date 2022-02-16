package spring.board.common.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import spring.board.common.SessionConst;
import spring.board.exception.member.UnauthenticatedUserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute(SessionConst.SESSION_LOGIN) == null) {
                response.sendRedirect("/login?redirectURL=" + request.getRequestURI());

                throw new UnauthenticatedUserException("로그인 이후 사용가능합니다.");
            }
        }

        return true;
    }
}
