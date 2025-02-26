package org.example.kiwii.controller.wordle;

import com.google.gson.Gson;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.dto.wordle.ResponseGetWordleQuizDTO;
import org.example.kiwii.service.wordle.WordleService;
import org.example.kiwii.vo.wordle.WordleQuizVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "wordleQuiz", value = "/api/wordle-quizzes")
public class WordleQuizServlet extends HttpServlet {
    // 현재 문제 가져오기
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WordleService wordleService = new WordleService();
        WordleQuizVO wordleQuiz = wordleService.getCurrentQuiz();

        ApiResponse<ResponseGetWordleQuizDTO> responseData = new ApiResponse<>();
        responseData.setStatus(200);

        if (wordleQuiz == null) {
            responseData.setMessage("문제가 설정되지 않았습니다.");
        } else {
            responseData.setData(new ResponseGetWordleQuizDTO(wordleQuiz));
        }
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(responseData));
    }
}
