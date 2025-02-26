package org.example.kiwii.dao.komantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.komantle.KomantleVO;

public class KomantleTrialDAO {
    private final SqlSession sqlSession;

    public KomantleTrialDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public KomantleVO tryAnswer(String word) {
        try {
            return sqlSession.selectOne("KomantleTrial.selectCosineSimilarity", word);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean isWord(String word) {
        try {
            return sqlSession.selectOne("KomantleTrial.isWord", word) != null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertTodayWord(String word) {
        try {
            sqlSession.insert("KomantleTrial.insertTodayWord", word);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertTrials(KomantleVO komantleVO) {
        try {
            sqlSession.insert("KomantleTrial.insertTrials", komantleVO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
