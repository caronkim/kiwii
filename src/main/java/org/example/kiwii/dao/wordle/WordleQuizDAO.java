package org.example.kiwii.dao.wordle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.wordle.WordleQuizVO;

public class WordleQuizDAO {
    private SqlSession sqlSession;

    public WordleQuizDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public WordleQuizVO selectCurrentQuiz() {
        return sqlSession.selectOne("wordlequizzes.selectCurrentQuiz");
    }

    public WordleQuizVO selectWordleQuizById(int quizId) {
        return sqlSession.selectOne("wordlequizzes.selectWordleQuizById", quizId);
    }

    public WordleQuizVO selectAvailableWordleQuizById(int quizId) {
        return sqlSession.selectOne("wordlequizzes.selectAvailableWordleQuizById", quizId);
    }
}
