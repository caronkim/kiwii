package org.example.kiwii.controller.stockUpDown;

import com.google.gson.Gson;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.stockupdown.StockUpDownService;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;
import org.example.kiwii.vo.stockupdown.StockUpDownVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static org.example.kiwii.CookieUtil.CookieUtil.getCookieValue;

@WebServlet("/api/stock-up-down/*")
public class StockUpDownServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final StockUpDownService stockUpDownService = new StockUpDownService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/predict")) {
            String userAnswer = req.getParameter("trial");
            String predictGameId = req.getParameter("gameId");
            String uuid = getCookieValue(req, "uuid");

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();

            StockUpDownTrialVO stockUpDownTrialVO = new StockUpDownTrialVO(uuid, predictGameId, userAnswer);
            // 사용자 입력 정답 DB에 저장
            stockUpDownService.insertStockUpDownTrial(stockUpDownTrialVO);

            // 응답 데이터 구성
            ApiResponse<String> apiResponse = new ApiResponse<>(200, "success", "Prediction recorded successfully");

            // JSON 응답 전송
            out.write(gson.toJson(apiResponse));
            out.flush();
            out.close();
        } else {
            // 잘못된 요청 처리
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid API endpoint");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = getCookieValue(req, "uuid");
        ApiResponse<StockUpDownVO> apiResponse;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        // 사용자의 예측 기록 조회
        StockUpDownVO stockUpDownVO = stockUpDownService.selectStockUpDownByUUID(uuid);

        if (stockUpDownVO == null) {
            // 사용자의 예측 기록이 없을 경우
            apiResponse = new ApiResponse<>(200, "no trial today", stockUpDownVO);
        } else {
            // 응답 데이터 구성
            apiResponse = new ApiResponse<>(200, "success", stockUpDownVO);
        }

        // JSON 응답 전송
        out.write(gson.toJson(apiResponse));
        out.flush();
        out.close();

    }
}
