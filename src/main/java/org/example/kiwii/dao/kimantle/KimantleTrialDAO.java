package org.example.kiwii.dao.kimantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.kimantle.KimantleVO;

import java.util.ArrayList;
import java.util.List;

public class KimantleTrialDAO {
    private final SqlSession sqlSession;

    public KimantleTrialDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public KimantleVO tryAnswer(String word) {
        try {
            return sqlSession.selectOne("KimantleTrial.selectCosineSimilarity", word);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean isWord(String word) {
        try {
            return sqlSession.selectOne("KimantleTrial.isWord", word) != null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertTodayWord(String word) {
        try {
            sqlSession.insert("KimantleTrial.insertTodayWord", word);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void insertTrials(KimantleVO kimantleVO, String word, String uuid) {
        kimantleVO.setUuid(uuid);
        kimantleVO.setWord(word);

        try {
            sqlSession.insert("KimantleTrial.insertTrials", kimantleVO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<KimantleVO> getRecentTrials(String uuid) {
        try {
            return sqlSession.selectList("KimantleTrial.getRecentTrials", uuid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getRecentTrials 실행 중 오류 발생: " + e.getMessage());
//            return new ArrayList<>();
        }
    }
}
