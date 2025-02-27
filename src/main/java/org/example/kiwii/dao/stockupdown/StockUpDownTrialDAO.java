package org.example.kiwii.dao.stockupdown;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;
import org.example.kiwii.vo.stockupdown.StockUpDownVO;

public class StockUpDownTrialDAO {
    private final SqlSession sqlSession;

    public StockUpDownTrialDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void insertStockUpDownTrial(StockUpDownTrialVO stockUpDownTrialVO) {
        try {
            sqlSession.insert("StockUpDownTrial.insertStockUpDownTrial", stockUpDownTrialVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertStockUpDownLog(StockUpDownVO stockUpDownVO) {
        try {
            sqlSession.insert("StockUpDownTrial.insertStockUpDownLog", stockUpDownVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
