package org.example.kiwii.service.dailyquiz;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.dailyquiz.DailyQuizDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.dailyquiz.DailyQuizVO;

import java.util.List;

public class DailyQuizService {
    public static List<DailyQuizVO> getTodayQuiz() {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        DailyQuizDAO dailyQuizDAO = new DailyQuizDAO(sqlSession);

        List<DailyQuizVO> list = null;
        list = dailyQuizDAO.getTodayQuiz();
        sqlSession.close();
        return list;
    }
}
