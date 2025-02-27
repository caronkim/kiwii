package org.example.kiwii.CookieUtil;

import org.example.kiwii.vo.user.UserVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    // ✅ 특정 쿠키 값 가져오기
    public static String getCookieValue(HttpServletRequest req, String name) {
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // ✅ 쿠키에서 `UserVO` 생성
    public static UserVO getUserFromCookies(HttpServletRequest req) {
        String uuidStr = getCookieValue(req, "uuid");
        String username = getCookieValue(req, "username");
        String accountId = getCookieValue(req, "accountId");

        // `uuid`는 숫자이므로 변환 처리
        if (uuidStr != null && username != null && accountId != null) {
            try {
                int uuid = Integer.parseInt(uuidStr); // `NUMBER` 타입 매칭
                return new UserVO(uuid, username, accountId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null; // 쿠키 정보가 부족하거나 변환 실패 시 null 반환
    }

    public static void setCookie(HttpServletResponse resp, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        // cookie.setHttpOnly(true); // JS 접근 방지 (보안 강화)
        cookie.setMaxAge(maxAge); // 30분 (1800초)
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        resp.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, "");
        // cookie.setHttpOnly(true); // JS 접근 방지 유지
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setPath("/"); // 기존 쿠키와 동일한 경로 설정 (중요)
        resp.addCookie(cookie);
    }

    public static boolean isLoginUser(HttpServletRequest req, int uuid) {
        String uuidFromCookie = getCookieValue(req, "uuid");
        String parsedUUID = Integer.toString(uuid);
        if (uuidFromCookie != null && uuidFromCookie.equals(parsedUUID)) {
            return true;
        }else {
            return false;
        }
    }
}
