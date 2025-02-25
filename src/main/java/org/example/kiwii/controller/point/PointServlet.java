package org.example.kiwii.controller.point;

import com.google.gson.Gson;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.point.PointService;
import org.example.kiwii.vo.point.PointHistoryVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/point/*")
public class PointServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final PointService pointService = new PointService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/history")) {
            String uuid = req.getParameter("uuid");

            boolean login = CookieUtil.isLoginUser(req, Integer.parseInt(uuid));

            if(!login) {
                ApiResponse apiResponse = new ApiResponse(200, "need login");
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(apiResponse));
                out.flush();
                out.close();

                //  로그인 페이지로 이동
            } else {
                List<PointHistoryVO> list;

                list = pointService.selectPointHistoryByUserUUID(uuid);

                if(list.isEmpty()){
                    ApiResponse apiResponse = new ApiResponse(200, "wrong uuid");
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(apiResponse));
                    out.flush();
                    out.close();
                }else {
                    ApiResponse<List<PointHistoryVO>> apiResponse = new ApiResponse<>(200, "success", list);
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(apiResponse));
                    out.flush();
                    out.close();
                }
            }
        }

        if (pathInfo.equals("/my")) {

        }
    }
}
