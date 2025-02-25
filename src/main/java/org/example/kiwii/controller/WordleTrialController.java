package org.example.kiwii.controller;

import com.google.gson.Gson;
import org.example.kiwii.dto.CheolHwanApiResponse;
import org.example.kiwii.dto.RequestPostWordleTrialDTO;
import org.example.kiwii.dto.ResponseGetWordleTrialDTO;
import org.example.kiwii.dto.ResponsePostWordleTrialDTO;
import org.example.kiwii.service.WordleService;
import org.example.kiwii.vo.WordleTrialVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "wordleTrial", value = "/wordle-trials")
public class WordleTrialController extends HttpServlet {
    // wordle 시도 내용 가져오기
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // todo:: session 에서 유저 가져오기
        int userId = Integer.parseInt(req.getParameter("userId"));
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        WordleService wordleService = new WordleService();
        List<WordleTrialVO> wordleTrials = wordleService.getWordleTrials(userId, quizId);

        Gson gson = new Gson();
        CheolHwanApiResponse<List<ResponseGetWordleTrialDTO>> responseData = new CheolHwanApiResponse<>();
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

        CheolHwanApiResponse<ResponsePostWordleTrialDTO> responseData = new CheolHwanApiResponse<>();
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
