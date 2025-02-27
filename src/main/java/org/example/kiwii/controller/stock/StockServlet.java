package org.example.kiwii.controller.stock;

import com.google.gson.Gson;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.stock.StockService;
import org.example.kiwii.vo.point.PointHistoryVO;
import org.example.kiwii.vo.stock.StockVO;
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
import java.util.Map;

@WebServlet("/api/stock/*")
public class StockServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final StockService stockService = new StockService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/give-random")) {
            System.out.println("/give-random called");

            UserVO loginUser = CookieUtil.getUserFromCookies(req);

            Integer userId = loginUser.getUuid();

            System.out.println(loginUser.getUsername());
            System.out.println(userId);

            if (userId == null) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(200, "wrong user");
                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
                return;
            }

            PointHistoryVO pointHistoryVO = new PointHistoryVO();
            pointHistoryVO.setUuid(userId);
            pointHistoryVO.setContent("랜덤 주식 뽑기");
            pointHistoryVO.setAmount(-500);

            //포인트 차감 한 후, 랜덤 주식 뽑아서 포인트 차감 기록에 기록하기
            PointHistoryVO randomStockHistory = stockService.giveRandomStock(pointHistoryVO);

            if (randomStockHistory == null) {
                ApiResponse<Object> apiResponse = new ApiResponse<>(200, "not enough point");
                PrintWriter out = resp.getWriter();
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
                return;
            }
            ApiResponse<PointHistoryVO> apiResponse = new ApiResponse<>(200, "success", randomStockHistory);
            PrintWriter out = resp.getWriter();
            out.write(gson.toJson(apiResponse));
            out.flush();
            out.close();
        }
    }
}
