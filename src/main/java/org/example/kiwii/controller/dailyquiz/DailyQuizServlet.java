package org.example.kiwii.controller.dailyquiz;

import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.dto.QuizAnswerDTO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.service.dailyquiz.DailyQuizService;
import org.example.kiwii.vo.dailyquiz.DailyQuizVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/dailyquiz/*")
public class DailyQuizServlet extends HttpServlet {
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        //test
        if (pathInfo.equals("/api/test")) {
            SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
            List<DailyQuizVO> list = sqlSession.selectList("DailyQuiz.selectAllQuiz");

            ApiResponse<List<DailyQuizVO>> response = new ApiResponse<>(200,"success", list);
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(response));
            out.flush();
        }

        if(pathInfo.equals("/api/todayquiz")) {
            List<DailyQuizVO> list = DailyQuizService.getTodayQuiz();
            if (list != null) {
                ApiResponse<List<DailyQuizVO>> response = new ApiResponse<>(200,"success", list);

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

        if (pathInfo.equals("/api/test")) {
            //{
            //  "userId": 123,
            //  "quizId": 456,
            //  "selectedAnswer": 2
            //} 로 데이터가 넘어옴
            int point = 0;

            BufferedReader in = req.getReader();
            try{
                QuizAnswerDTO answerDTO = gson.fromJson(in, QuizAnswerDTO.class);
                // 한 문제 당 5 포인트
                point += answerDTO.getCorrectAnswer()*5;

                // 0 점 획득시
                if (point == 0){
                    //  포인트 제공 없음
                    System.out.println("0점입니다");
                    ApiResponse<Object> response = new ApiResponse<>(200,"success");
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(response));
                    out.flush();
                    out.close();
                } else {
                    //  Point table에 point 추가
                    System.out.println("point table 업데이트");
                    //  User tabla에 point 업데이트
                    System.out.println("user table 업데이트");
                    System.out.println(answerDTO.getCorrectAnswer()+" "+answerDTO.getUserId());
                    String msg = answerDTO.getCorrectAnswer()+" "+answerDTO.getUserId();

                    ApiResponse<Object> response = new ApiResponse<>(200,"success"+msg);
                    PrintWriter out = resp.getWriter();
                    out.print(gson.toJson(response));
                    out.flush();
                    out.close();

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }






        }
    }
}
