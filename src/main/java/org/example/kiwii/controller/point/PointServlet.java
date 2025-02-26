package org.example.kiwii.controller.point;

import com.google.gson.Gson;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.point.PointService;
import org.example.kiwii.service.user.UserService;
import org.example.kiwii.vo.point.PointHistoryVO;
import org.example.kiwii.vo.user.UserVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/point/*")
public class PointServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserService userService = new UserService();
    private final PointService pointService = new PointService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/deposit")) {
            BufferedReader in = req.getReader();

            PointHistoryVO pointHistoryVO = gson.fromJson(in, PointHistoryVO.class);

            Integer afterPoint = userService.depositPointByUserUUID(pointHistoryVO);

            if (afterPoint == null) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(200, "wrong user");
                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("afterPoint", afterPoint);
                ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>(200, "success", data);

                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();


            }
        }

        if (pathInfo.equals("/use")) {
            BufferedReader in = req.getReader();

            PointHistoryVO pointHistoryVO = gson.fromJson(in, PointHistoryVO.class);

            Integer afterPoint = userService.usePointByUserUUID(pointHistoryVO);

            if (afterPoint == null) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(200, "wrong user");
                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
            } else if (afterPoint < 0) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(200, "not enough point");
                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("afterPoint", afterPoint);
                ApiResponse<Map<String,Object>> apiResponse = new ApiResponse<>(200, "success", data);

                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
                in.close();


            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/history")) {

            UserVO loginUser = CookieUtil.getUserFromCookies(req);

            List<PointHistoryVO> list;

            list = pointService.selectPointHistoryByUserUUID(loginUser.getUuid());

            if (list.isEmpty()) {
                ApiResponse apiResponse = new ApiResponse(200, "wrong uuid");
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
            } else {
                ApiResponse<List<PointHistoryVO>> apiResponse = new ApiResponse<>(200, "success", list);
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();
            }

        }

    }
}
