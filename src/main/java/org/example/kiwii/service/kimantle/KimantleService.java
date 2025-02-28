package org.example.kiwii.service.kimantle;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.kimantle.KimantleTrialDAO;
import org.example.kiwii.dao.point.PointDAO;
import org.example.kiwii.dao.user.UserDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.kimantle.KimantleVO;
import org.example.kiwii.vo.point.PointHistoryVO;

import java.util.List;

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

    public KimantleVO tryAnswer(String word, int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        KimantleVO result = null;
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            result = kimantleTrialDAO.tryAnswer(word);
            if (result.getRank() == 0) {
                Integer trialCnt = kimantleTrialDAO.getTrialCnt(String.valueOf(uuid));
                System.out.println("trialCnt는 " + trialCnt);
                if (trialCnt != null) {
                    int point = 200 - (trialCnt) * 10;
                    UserDAO userDAO = new UserDAO(sqlSession);
                    PointDAO pointDAO = new PointDAO(sqlSession);
                    try {
                        Integer beforePoint = userDAO.selectUserPointByUserUUID(uuid);
                        Integer beforeTotalEarnedPoint = userDAO.selectUserTotalEarnedPointByUserUUID(uuid);

                        if (beforePoint == null || beforeTotalEarnedPoint == null) {
                            sqlSession.rollback();
                            return null;  // 사용자 정보 없음
                        }

                        int afterPoint = beforePoint + point;
                        int afterTotalEarnedPoint = beforeTotalEarnedPoint + point;

                        // ✅ user point update
                        userDAO.updateUserPointByUserUUID(uuid, afterPoint);

                        // ✅ user Total earned point update
                        userDAO.updateUserTotalEarnedPointByUserUUID(uuid, afterTotalEarnedPoint);

                        // ✅ point history insert
                        PointHistoryVO pointHistoryVO = new PointHistoryVO();
                        pointHistoryVO.setUuid(uuid);
                        pointHistoryVO.setAmount(point);
                        pointHistoryVO.setContent("kimantle 정답");

                        pointDAO.insertPointHistory(pointHistoryVO);
                        sqlSession.commit();
                    } catch (Exception e) {
                            sqlSession.rollback();
                    }
            }}
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
            return null;
        } finally {
            sqlSession.close();
        }

        return result;
    }

    public int insertTrials(KimantleVO kimantleVO, String word, int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            int inserted = kimantleTrialDAO.insertTrials(kimantleVO, word, uuid);
            if (inserted == 0) {
                sqlSession.rollback();
                return 0;
            } else {
                sqlSession.commit();
                return inserted;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
            return 0;
        } finally {
            sqlSession.close();
        }
    }

    public List<KimantleVO> getRecentTrials(int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            return kimantleTrialDAO.getRecentTrials(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            sqlSession.close();
        }
    }

    public Integer getTrialCnt(String uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            KimantleTrialDAO kimantleTrialDAO = new KimantleTrialDAO(sqlSession);
            return kimantleTrialDAO.getTrialCnt(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            sqlSession.close();
        }
    }
}
