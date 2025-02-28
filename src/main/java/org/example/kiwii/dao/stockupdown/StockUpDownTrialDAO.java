package org.example.kiwii.dao.stockupdown;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;

public class StockUpDownTrialDAO {
    private final SqlSession sqlSession;

    public StockUpDownTrialDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 유저의 주식 상승, 하락 예측을 저장하는 메서드
    public int insertStockUpDownTrial(StockUpDownTrialVO stockUpDownTrialVO) {
        try {
            return sqlSession.insert("StockUpDownTrial.insertStockUpDownTrial", stockUpDownTrialVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    // 어제의 예측한 값을 자동으로 체점하고 결과를 저장하는 메서드
    public int insertStockUpDownLog() {
        try {
            return sqlSession.insert("StockUpDownTrial.insertStockUpDownLog");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public StockUpDownTrialVO selectStockUpDownByUUID(int uuid) {
        try {
            return sqlSession.selectOne("StockUpDownTrial.selectStockUpDownByUUID", uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String selectTodayCompanyName(){
        try {
            return sqlSession.selectOne("StockUpDownTrial.selectTodayCompanyName");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
