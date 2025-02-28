package org.example.kiwii.dao.kimantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.kimantle.KimantleVO;

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
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean isWord(String word) {
        try {
            KimantleVO result = sqlSession.selectOne("KimantleTrial.isWord", word);
            return result != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void insertTodayWord(String word) {
        try {
            sqlSession.insert("KimantleTrial.insertTodayWord", word);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int insertTrials(KimantleVO kimantleVO, String word, int uuid) {
        kimantleVO.setUuid(uuid);
        kimantleVO.setWord(word);
        try {
            return sqlSession.insert("KimantleTrial.insertTrials", kimantleVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public List<KimantleVO> getRecentTrials(int uuid) {
        try {
            System.out.println("현재 uuid : " + uuid);
            return sqlSession.selectList("KimantleTrial.getRecentTrials", String.valueOf(uuid));
        } catch (Exception e) {
            throw new RuntimeException("getRecentTrials 실행 중 오류 발생: " + e.getMessage());
        }
    }

    public Integer getTrialCnt(String uuid) {
        try {
            return sqlSession.selectOne("KimantleTrial.getTrialCnt", uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
