package org.example.kiwii.service.kimantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.kimantle.KimantleTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;

public class KimantleService {
    public boolean isWord(String word) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            return kimantleTrialDAO.isWord(word);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
            return false;
        } finally {
            sqlSession.close();
        }
    }
}
