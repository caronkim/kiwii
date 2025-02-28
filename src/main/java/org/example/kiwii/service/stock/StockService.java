package org.example.kiwii.service.stock;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.point.PointDAO;
import org.example.kiwii.dao.stock.StockDAO;
import org.example.kiwii.dao.user.UserDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.point.PointHistoryVO;
import org.example.kiwii.vo.stock.StockVO;

public class StockService {
    public PointHistoryVO giveRandomStock(PointHistoryVO pointHistoryVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        try {
            // 1️⃣ 사용자 현재 포인트 조회
            UserDAO userDAO = new UserDAO(sqlSession);
            int beforePoint = userDAO.selectUserPointByUserUUID(pointHistoryVO.getUuid());

            if (beforePoint < Math.abs(pointHistoryVO.getAmount())) {
                return null;
            }

            // 2️⃣ 포인트 차감 후 업데이트
            int afterPoint = beforePoint + pointHistoryVO.getAmount();
            userDAO.updateUserPointByUserUUID(pointHistoryVO.getUuid(), afterPoint);

            // 3️⃣ 확률 기반 랜덤 주식 선택
            StockDAO stockDAO = new StockDAO(sqlSession);
            /// random number
            int randomNumber = (int) (Math.random() * 10) + 1;

            StockVO randomStocks = stockDAO.selectStockById(randomNumber); // 1개 지급

            // 4️⃣ 포인트 변동 기록 저장 (PointHistoryVO 활용)
            PointDAO pointDAO = new PointDAO(sqlSession);
            pointHistoryVO.setContent("랜덤 주식 뽑기 "+ randomStocks.getName());
            pointDAO.insertPointHistory(pointHistoryVO);

            // 트랜잭션 커밋
            sqlSession.commit();
            return pointHistoryVO;
        } catch (Exception e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }
}
