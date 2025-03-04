package org.example.kiwii.controller.stockUpDown;

import com.google.gson.Gson;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.service.stockupdown.StockUpDownService;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;

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
        ApiResponse<String> apiResponse;

        if (pathInfo.equals("/predict")) {
            String userAnswer = (String) map.get("trial");
            String predictGameId = "";
//            String predictGameId = (String) map.get("gameId");
            String uuidStr = getCookieValue(req, "uuid");
            int uuid = Integer.parseInt(uuidStr);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();

            StockUpDownTrialVO stockUpDownTrialVO = new StockUpDownTrialVO(uuid, predictGameId, userAnswer);

            if ("O".equals(userAnswer) || "X".equals(userAnswer)) {
                // 사용자 입력 정답 DB에 저장
                int inserted = stockUpDownService.insertStockUpDownTrial(stockUpDownTrialVO);

                if (inserted == 0) {
                    // 실패 시
                    apiResponse = new ApiResponse<>(400, "fail", "Failed to record prediction");

                } else {
                    // 응답 데이터 구성
                    apiResponse = new ApiResponse<>(200, "success", "Prediction recorded successfully");
                }
                // JSON 응답 전송
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
            } else {
                // 사용자 입력이 O 또는 X가 아닌 경우
                apiResponse = new ApiResponse<>(400, "fail", "Not a valid answer");
                out.write(gson.toJson(apiResponse));
                out.flush();
                out.close();
            }

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
        StockUpDownTrialVO stockUpDownVO = stockUpDownService.selectStockUpDownByUUID(uuid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("companyName", comapanyName);
        map.put("stockUpDownVO", stockUpDownVO);

        System.out.println(map);

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
