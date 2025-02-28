package org.example.kiwii.service.kimantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.kimantle.KimantleTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.kimantle.KimantleVO;

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

    public KimantleVO tryAnswer(String word) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            return kimantleTrialDAO.tryAnswer(word);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
            return null;
        } finally {
            sqlSession.close();
        }
    }

    public void insertTrials(KimantleVO kimantleVO, String word, String uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            kimantleTrialDAO.insertTrials(kimantleVO, word, uuid);
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }
}
