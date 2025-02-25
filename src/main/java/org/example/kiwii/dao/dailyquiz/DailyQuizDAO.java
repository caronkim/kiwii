package org.example.kiwii.dao.dailyquiz;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.dailyquiz.DailyQuizVO;

import java.util.List;

public class DailyQuizDAO {
    private SqlSession sqlSession;

    public DailyQuizDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public List<DailyQuizVO> getTodayQuiz() {
        List<DailyQuizVO> list;
        try{
            list = sqlSession.selectList("DailyQuiz.selectTodayQuiz");
            return list;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
