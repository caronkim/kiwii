package org.example.kiwii.controller.wordle;

import com.google.gson.Gson;
import org.example.kiwii.dto.ApiResponse;
import org.example.kiwii.dto.wordle.RequestPostWordleTrialDTO;
import org.example.kiwii.dto.wordle.ResponseGetWordleTrialDTO;
import org.example.kiwii.dto.wordle.ResponsePostWordleTrialDTO;
import org.example.kiwii.service.wordle.WordleService;
import org.example.kiwii.vo.wordle.WordleTrialVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "wordleTrial", value = "/api/wordle-trials")
public class WordleTrialServlet extends HttpServlet {
    // wordle 시도 내용 가져오기
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // todo:: session 에서 유저 가져오기
        int userId = Integer.parseInt(req.getParameter("userId"));
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        WordleService wordleService = new WordleService();
        List<WordleTrialVO> wordleTrials = wordleService.getWordleTrials(userId, quizId);

        Gson gson = new Gson();
        ApiResponse<List<ResponseGetWordleTrialDTO>> responseData = new ApiResponse<>();
        List<ResponseGetWordleTrialDTO> trials = new ArrayList<>();
        for (WordleTrialVO wordleTrial : wordleTrials) {
            trials.add(new ResponseGetWordleTrialDTO(wordleTrial));
        }
        responseData.setStatus(200);
        responseData.setData(trials);
        resp.getWriter().print(gson.toJson(responseData));
    }

    // wordle 시도하기
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        RequestPostWordleTrialDTO requestData = gson.fromJson(req.getReader(), RequestPostWordleTrialDTO.class);

        WordleService wordleService = new WordleService();
        WordleTrialVO wordleTrial = wordleService.tryAnswer(requestData.getUserId(), requestData.getQuizId(), requestData.getCharacters());

        ApiResponse<ResponsePostWordleTrialDTO> responseData = new ApiResponse<>();
        responseData.setStatus(200);
        if (wordleTrial == null) {
            responseData.setMessage("fail");
            responseData.setData(null);
        } else {
            responseData.setMessage("success");
            responseData.setData(new ResponsePostWordleTrialDTO(wordleTrial));
        }
        resp.getWriter().print(gson.toJson(responseData));
    }
}
