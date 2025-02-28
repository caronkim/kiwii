package org.example.kiwii.service.stockupdown;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.stockupdown.StockUpDownTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.stockupdown.StockUpDownTrialVO;
import org.example.kiwii.vo.stockupdown.StockUpDownVO;

public class StockUpDownService {
    public int insertStockUpDownTrial(StockUpDownTrialVO stockUpDownTrialVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
             StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
             if (stockUpDownTrialDAO.insertStockUpDownTrial(stockUpDownTrialVO) == 0) {
                 sqlSession.rollback();
                 return 0;
             } else {
                 sqlSession.commit();
                 return 1;
             }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
            return 0;
        } finally {
            sqlSession.close();
        }
    }

    public int insertStockUpDownLog() {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
            int inserted = stockUpDownTrialDAO.insertStockUpDownLog();
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

    public StockUpDownVO selectStockUpDownByUUID(int uuid) {
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

    public String selectTodayCompanyName(){
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            StockUpDownTrialDAO stockUpDownTrialDAO = new StockUpDownTrialDAO(sqlSession);
            return stockUpDownTrialDAO.selectTodayCompanyName();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            sqlSession.close();
        }
    }
}
