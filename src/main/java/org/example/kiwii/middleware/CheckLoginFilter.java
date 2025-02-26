package org.example.kiwii.middleware;

import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.vo.user.UserVO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*") // 모든 요청에 대해 실행
public class CheckLoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        // 🔹 로그인 페이지는 필터를 우회하도록 설정 (무한 리디렉트 방지)
        if (requestURI.startsWith("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔹 쿠키에서 유저 정보 가져오기
        UserVO user = CookieUtil.getUserFromCookies(req);

        if (user == null) {
            System.out.println("🔒 need login, /login redirect");
            // res.sendRedirect("/login");  // 실제 리디렉트 로직 (구현 후 활성화)
            /// 아래 코드 지워야함!!
            chain.doFilter(request, response);
            return;
        }

        // ✅ 로그인된 경우 요청 통과
        System.out.println(" signed in user");
        chain.doFilter(request, response);
    }
}
