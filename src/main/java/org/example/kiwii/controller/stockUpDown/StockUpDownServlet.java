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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.example.kiwii.CookieUtil.CookieUtil.getCookieValue;

@WebServlet("/api/stock-up-down/*")
public class StockUpDownServlet extends HttpServlet {
    private final StockUpDownService stockUpDownService = new StockUpDownService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);

        if (pathInfo.equals("/predict")) {
            String userAnswer = (String) map.get("trial");
            String predictGameId = "";
//            String predictGameId = (String) map.get("gameId");
            String uuid = getCookieValue(req, "uuid");

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();

            StockUpDownTrialVO stockUpDownTrialVO = new StockUpDownTrialVO(uuid, predictGameId, userAnswer);
//            StockUpDownTrialVO stockUpDownTrialVO = new StockUpDownTrialVO(uuid, userAnswer);
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
        String uuidStr = getCookieValue(req, "uuid");
        int uuid = Integer.parseInt(uuidStr);
        ApiResponse<Object> apiResponse;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        // 오늘의 문제 조회
        String comapanyName = stockUpDownService.selectTodayCompanyName();

        // 사용자의 예측 기록 조회
        StockUpDownVO stockUpDownVO = stockUpDownService.selectStockUpDownByUUID(uuid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("companyName", comapanyName);
        map.put("stockUpDownVO", stockUpDownVO);

        if (stockUpDownVO == null) {
            // 사용자의 예측 기록이 없을 경우
            apiResponse = new ApiResponse<>(200, "no trial today", map);
        } else {
            // 응답 데이터 구성
            apiResponse = new ApiResponse<>(200, "success", map);
        }

        // JSON 응답 전송
        out.write(gson.toJson(apiResponse));
        out.flush();
        out.close();

    }
}
