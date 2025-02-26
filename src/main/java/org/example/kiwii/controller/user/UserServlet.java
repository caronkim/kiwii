package org.example.kiwii.controller.user;

import com.google.gson.Gson;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.user.UserService;
import org.example.kiwii.vo.user.UserVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/user/*")
public class UserServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/information")) {
            UserVO loginUser = CookieUtil.getUserFromCookies(req);

            if (loginUser == null) {
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "error");

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
            }

            UserVO selectedUser = userService.selectUserByUserUUID(loginUser.getUuid());
            if(selectedUser != null) {
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "success", selectedUser);

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
            }else {
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "error");

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/login")) {
            BufferedReader in = req.getReader();
            UserVO loginTryUser = gson.fromJson(in, UserVO.class);

            UserVO loginSuccessUser = userService.loginByUserVO(loginTryUser);

            if(loginSuccessUser != null){
                //  login success
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "login success", loginSuccessUser);

                // ✅ 로그인 성공 - 쿠키 저장 (30분 유지)
                CookieUtil.setCookie(resp, "uuid", String.valueOf(loginSuccessUser.getUuid()), 1800);
                CookieUtil.setCookie(resp, "username", loginSuccessUser.getUsername(), 1800);
                CookieUtil.setCookie(resp, "accountId", loginSuccessUser.getAccountId(), 1800);

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();

            }else {
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "login fail");

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();
            }
        }

        if(pathInfo.equals("/register")) {
            BufferedReader in = req.getReader();
            UserVO registerTryUser = gson.fromJson(in, UserVO.class);

            UserVO registeredUser = userService.insertUser(registerTryUser);

            if(registeredUser == null){
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "error");
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();
            }

            if(registeredUser != null) {
                ApiResponse<UserVO> apiResponse = new ApiResponse<>(200, "success", registeredUser);
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();
            }

        }
    }



}
