package org.example.kiwii.service.stockupdown;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.stockupdown.StockUpDownTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;
import org.example.kiwii.vo.stockupdown.StockUpDownVO;

public class StockUpDownService {
    public void insertStockUpDownTrial(StockUpDownTrialVO stockUpDownTrialVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
             StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
             stockUpDownTrialDAO.insertStockUpDownTrial(stockUpDownTrialVO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public void insertStockUpDownLog() {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
            stockUpDownTrialDAO.insertStockUpDownLog();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

    public StockUpDownVO selectStockUpDownByUUID(String uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
            return stockUpDownTrialDAO.selectStockUpDownByUUID(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            sqlSession.close();
        }
    }
}
