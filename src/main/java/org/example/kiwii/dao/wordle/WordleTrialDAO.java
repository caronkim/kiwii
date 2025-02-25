package org.example.kiwii.dao.wordle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.wordle.WordleTrialVO;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class WordleTrialDAO {
    private SqlSession sqlSession;

    public WordleTrialDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<WordleTrialVO> selectWordleTrialsByUserIdAndQuizId(int userId, int quizId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("quizId", quizId);
        return sqlSession.selectList("wordletrials.selectWordleTrialsByUserIdAndQuizId", params);
    }

    public int selectWordleTrialCount(int userId, int quizId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("quizId", quizId);
        return sqlSession.selectOne("wordletrials.selectWordleTrialCount", params);
    }

    public WordleTrialVO selectAnswerWordleTrial(int userId, int quizId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("quizId", quizId);
        return sqlSession.selectOne("wordletrials.selectAnswerWordleTrial", params);
    }

    public boolean insertWordleTrial(WordleTrialVO wordleTrial) {
        return sqlSession.insert("wordletrials.insertWordleTrial", wordleTrial) == 1;
    }
}
