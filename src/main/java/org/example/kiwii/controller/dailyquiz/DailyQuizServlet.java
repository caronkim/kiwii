package org.example.kiwii.controller.dailyquiz;

import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.CookieUtil.CookieUtil;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.dto.dailyquiz.QuizAnswerDTO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.service.dailyquiz.DailyQuizService;
import org.example.kiwii.service.user.UserService;
import org.example.kiwii.vo.dailyquiz.DailyQuizVO;
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
import java.util.List;
import java.util.Map;

@WebServlet("/api/dailyquiz/*")
public class DailyQuizServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/todayquiz")) {
            List<DailyQuizVO> list = DailyQuizService.getTodayQuiz();
            if (list != null) {
                ApiResponse<List<DailyQuizVO>> response = new ApiResponse<>(200, "success", list);

                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(response));
                out.flush();
            } else {
                ApiResponse<Object> response = new ApiResponse<>(500, "error");
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(response));
                out.flush();
            }


        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/submit")) {
            //{
            //  "correctAnswer": 2
            //} 로 데이터가 넘어옴
            int point = 0;

            UserVO loginUser = CookieUtil.getUserFromCookies(req);

            BufferedReader in = req.getReader();
            Map<String, Object> map = gson.fromJson(in, Map.class);

            Integer correctAnswer = ((Number) map.get("correctAnswer")).intValue();


            if (correctAnswer == null) {
                ApiResponse<Object> response = new ApiResponse<>(200, "error");
                PrintWriter out = resp.getWriter();
                out.print(gson.toJson(response));
                out.flush();
                out.close();
                return;
            }

            try {
                // quiz 정답갯수 로딩
                QuizAnswerDTO answerDTO = new QuizAnswerDTO();
                answerDTO.setUserId(loginUser.getUuid());
                answerDTO.setCorrectAnswer(correctAnswer);

                // 한 문제 당 5 포인트
                point += answerDTO.getCorrectAnswer() * 5;


                // PointHistoryVO 에 answerDTO 에서 받아온 값을 받아 전달
                PointHistoryVO pointHistoryVO = new PointHistoryVO();
                pointHistoryVO.setUuid(answerDTO.getUserId());
                pointHistoryVO.setAmount(point);
                pointHistoryVO.setContent("퀴즈 정답");

                // 0 점 획득시
                if (point == 0) {
                    //  포인트 제공 없음
                    ApiResponse<Object> response = new ApiResponse<>(200, "no point");
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(response));
                    out.flush();
                    out.close();
                }


                if (point != 0) {

                    // 포인트 제공 후 포인트 반환
                    Integer afterPoint = userService.depositPointByUserUUID(pointHistoryVO);

                    ApiResponse<Object> response = new ApiResponse<>(200, "success", afterPoint);
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(response));
                    out.flush();
                    out.close();
                    in.close();
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
