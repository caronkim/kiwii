package org.example.kiwii.service.wordle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.wordle.WordleQuizDAO;
import org.example.kiwii.dao.wordle.WordleTrialDAO;
import org.example.kiwii.dao.wordle.WordleWordDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.wordle.WordleQuizVO;
import org.example.kiwii.vo.wordle.WordleTrialVO;

import java.util.List;

public class WordleService {
    public WordleQuizVO getCurrentQuiz() {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            WordleQuizDAO wordleQuizDAO = new WordleQuizDAO(sqlSession);
            return wordleQuizDAO.selectCurrentQuiz();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public List<WordleTrialVO> getWordleTrials(int userId, int quizId) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            WordleTrialDAO wordleTrialDAO = new WordleTrialDAO(sqlSession);
            return wordleTrialDAO.selectWordleTrialsByUserIdAndQuizId(userId, quizId);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public WordleTrialVO tryAnswer(int userId, int quizId, String characters) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            // 1. 단어 확인
            WordleWordDAO wordleWordsDAO = new WordleWordDAO(sqlSession);
            boolean isValid = wordleWordsDAO.selectWordleWordByCharacters(characters) != 0;
            if (!isValid) {
                return null;
            }

            WordleTrialDAO wordleTrialDAO = new WordleTrialDAO(sqlSession);
            // 2. 이미 정답 확인
            WordleTrialVO answerTrial = wordleTrialDAO.selectAnswerWordleTrial(userId, quizId);
            if (answerTrial != null) {
                return null;
            }

            // 3. wordle 시도 횟수 확인
            int trialCount = wordleTrialDAO.selectWordleTrialCount(userId, quizId);
            System.out.println("trialCount = " + trialCount);
            if (trialCount >= 6) {
                return null;
            }

            // 4. 시간 확인
            WordleQuizDAO wordleQuizDAO = new WordleQuizDAO(sqlSession);
            WordleQuizVO wordleQuiz = wordleQuizDAO.selectAvailableWordleQuizById(quizId);
            if (wordleQuiz == null) {
                return null;
            }

            // 5. 정답 로직 수행
            List<Character> answerCharacters = wordleQuiz.getWord().getCharacterList();
            WordleTrialVO newTrial = new WordleTrialVO();
            newTrial.setQuizId(quizId);
            newTrial.setUuid(userId);
            newTrial.setCharacters(characters);

            // 시도 실패
            if (!newTrial.checkAnswer(answerCharacters)) {
                return null;
            }

            // DB insert
            if (!wordleTrialDAO.insertWordleTrial(newTrial)) {
                sqlSession.rollback();
                return null;
            }

            // todo:: 포인트 처리


            sqlSession.commit();
            return newTrial;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
