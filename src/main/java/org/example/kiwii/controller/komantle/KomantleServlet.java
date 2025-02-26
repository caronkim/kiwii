package org.example.kiwii.controller.komantle;

import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dao.komantle.KomantleTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.komantle.KomantleVO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/komantle")
public class KomantleServlet extends HttpServlet {

    private SqlSessionFactory sqlSessionFactory;
    private KomantleTrialDAO komantleTrialDAO;

    @Override
    public void init() {
        sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userWord = request.getParameter("word");
        String uuid = request.getParameter("uuid");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            komantleTrialDAO = new KomantleTrialDAO(sqlSession);

            // 1. 입력한 단어가 사전에 있는지 확인
            boolean exists = komantleTrialDAO.isWord(userWord);
            jsonResponse.put("exists", exists);

            if (!exists) {
                // 사전에 없는 단어일 경우 클라이언트에게 알림
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", "단어가 사전에 없습니다. 다시 입력해주세요.");
            } else {
                // 2. 유사도 및 순위 조회
                KomantleVO result = komantleTrialDAO.tryAnswer(userWord);

                if (result != null) {
                    jsonResponse.put("rank", result.getRank());
                    jsonResponse.put("similarity", result.getCosineSimilarity());

                    // 3. 입력 로그 저장
                    komantleTrialDAO.insertTrials(result, userWord, uuid);
                    sqlSession.commit(); // 트랜잭션 반영
                    jsonResponse.put("status", "success");
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "단어가 오늘의 순위에 없습니다.");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        }

        // Gson을 사용하여 Map을 JSON 문자열로 변환
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}
