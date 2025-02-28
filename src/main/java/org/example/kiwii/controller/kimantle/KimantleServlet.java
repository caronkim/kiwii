package org.example.kiwii.controller.kimantle;

import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dao.kimantle.KimantleTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.service.kimantle.KimantleService;
import org.example.kiwii.vo.kimantle.KimantleVO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/kimantle")
public class KimantleServlet extends HttpServlet {

    private SqlSessionFactory sqlSessionFactory;
    private KimantleTrialDAO kimantleTrialDAO;

    @Override
    public void init() {
        sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // CORS 설정 추가
        response.setHeader("Access-Control-Allow-Origin", "*"); // 모든 도메인 허용 (배포 시 특정 도메인으로 변경)
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String userWord = request.getParameter("word");
        String uuid = CookieUtil.getCookieValue(request, "uuid");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();
        KimantleService kimantleService = new KimantleService();


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
                jsonResponse.put("rank", result.getRank());
                jsonResponse.put("similarity", result.getCosineSimilarity());

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
        // CORS 설정 추가
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String uuid = request.getParameter("uuid");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            kimantleTrialDAO = new KimantleTrialDAO(sqlSession);

            if (uuid == null || uuid.isEmpty()) {
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", "UUID가 필요합니다.");
            } else {
                List<KimantleVO> recentTrials = kimantleTrialDAO.getRecentTrials(uuid);
                jsonResponse.put("status", "success");
                jsonResponse.put("history", recentTrials);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());  // 🔥 예외 메시지를 반환
//            e.printStackTrace(); // 콘솔에 로그 출력
        }

        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}
