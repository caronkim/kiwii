package org.example.kiwii.controller.kimantle;

import com.google.gson.Gson;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.service.kimantle.KimantleService;
import org.example.kiwii.service.user.UserService;
import org.example.kiwii.vo.kimantle.KimantleVO;
import org.example.kiwii.vo.point.PointHistoryVO;

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

import static org.example.kiwii.CookieUtil.CookieUtil.getCookieValue;

@WebServlet("/api/kimantle")
public class KimantleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        String userWord = (String) map.get("word");
        String uuidStr = CookieUtil.getCookieValue(request, "uuid");
        int uuid = Integer.parseInt(uuidStr);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> jsonResponse = new HashMap<>();
        KimantleService kimantleService = new KimantleService();

        // 정답시 1 아닐시 0
        int correct = 0;


        // 1. 입력한 단어가 사전에 있는지 확인
        boolean exists = kimantleService.isWord(userWord);
        jsonResponse.put("exists", exists);

        if (!exists) {
            // 사전에 없는 단어일 경우 클라이언트에게 알림
            jsonResponse.put("status", "fail");
            jsonResponse.put("message", "단어가 사전에 없습니다. 다시 입력해주세요.");
        } else {
            // 2. 유사도 및 순위 조회
            KimantleVO result = kimantleService.tryAnswer(userWord);

            if (result != null) {
                int rank = result.getRank();
                jsonResponse.put("rank", rank);
                jsonResponse.put("similarity", result.getCosineSimilarity());
                // 정답 시
                if (rank == 0){
                    // 클라이언트에 정답을 알림
                    correct = 1;
                    // 점수 증가
                    PointHistoryVO pointHistoryVO = new PointHistoryVO();
                    pointHistoryVO.setUuid(uuid);
                    pointHistoryVO.setContent("Kimantle 정답");
                    pointHistoryVO.setAmount(200);

                    UserService userService = new UserService();
                    userService.depositPointByUserUUID(pointHistoryVO);
                }
                jsonResponse.put("correct", correct);

                // 3. 입력 로그 저장
                kimantleService.insertTrials(result, userWord, uuid);
                jsonResponse.put("status", "success");
            } else {
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", "단어가 오늘의 순위에 없습니다.");
            }
       }

        // Gson을 사용하여 Map을 JSON 문자열로 변환
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String uuidStr = getCookieValue(request, "uuid");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();
        KimantleService kimantleService = new KimantleService();

        if (uuidStr == null || uuidStr.isEmpty()) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "UUID가 필요합니다.");
        } else {
            int uuid = Integer.parseInt(uuidStr);
            List<KimantleVO> recentTrials = kimantleService.getRecentTrials(uuid);
            if (recentTrials.isEmpty()) {
                jsonResponse.put("message", "최근 기록이 없습니다.");
            } else {
                jsonResponse.put("message", "최근 기록이 있습니다");
            }
            jsonResponse.put("status", "success");
            jsonResponse.put("history", recentTrials);
        }

        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}
