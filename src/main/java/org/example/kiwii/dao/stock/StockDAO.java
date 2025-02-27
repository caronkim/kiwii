package org.example.kiwii.dao.stock;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.stock.StockVO;

public class StockDAO {
    private final SqlSession sqlSession;
    public StockDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public StockVO selectStockById(int randomNumber) {
        StockVO stockVO = null;
        try{
            stockVO = sqlSession.selectOne("Stock.selectStockById", randomNumber);
            return stockVO;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return stockVO;
        }
    }
}
