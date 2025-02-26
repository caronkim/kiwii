package org.example.kiwii.dao.point;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.point.PointHistoryVO;

import java.util.ArrayList;
import java.util.List;

public class PointDAO {
    private final SqlSession sqlSession;

    public PointDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<PointHistoryVO> selectPointHistoryByUserUUID(int uuid) {
        List<PointHistoryVO> list = new ArrayList<>();

        try{
            list = sqlSession.selectList("PointHistory.selectPointHistoryByUserUUID", uuid);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void insertPointHistory(PointHistoryVO pointHistoryVO) {
        try{
            sqlSession.insert("PointHistory.insertPointHistory", pointHistoryVO);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
