package org.example.kiwii.service.point;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.point.PointDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.point.PointHistoryVO;

import java.util.ArrayList;
import java.util.List;

public class PointService {
    public List<PointHistoryVO> selectPointHistoryByUserUUID(int uuid) {

        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();

        PointDAO pointDAO = new PointDAO(sqlSession);
        List<PointHistoryVO> list = new ArrayList<>();
        list = pointDAO.selectPointHistoryByUserUUID(uuid);

        sqlSession.close();

        return  list;
    }
}
