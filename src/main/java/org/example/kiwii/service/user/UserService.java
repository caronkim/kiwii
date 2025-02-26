package org.example.kiwii.service.user;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.point.PointDAO;
import org.example.kiwii.dao.user.UserDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.point.PointHistoryVO;
import org.example.kiwii.vo.user.UserVO;

public class UserService {

    public UserVO loginByUserVO(UserVO loginUser) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser= userDAO.selectUserByUsername(loginUser.getUsername());

        if(selectedUser == null){
            sqlSession.close();
            //username 유저가 존재하지 않을 경우
            return null;
        }
        if(selectedUser.getPassword().equals(loginUser.getPassword())){
            sqlSession.close();
            return selectedUser;
        } else {
            sqlSession.close();
            return null;
        }


    }

    public UserVO selectUserByUserUUID(int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser= userDAO.selectUserByUserUUID(uuid);

        if(selectedUser == null){
            //username 유저가 존재하지 않을 경우
            sqlSession.close();
            return null;
        }else {
            sqlSession.close();
            return selectedUser;
        }

    }

    public Integer depositPointByUserUUID(PointHistoryVO pointHistoryVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);
        PointDAO pointDAO = new PointDAO(sqlSession);
        Integer beforePoint = userDAO.selectUserPointByUserUUID(pointHistoryVO.getUuid());
        if(beforePoint == null){
            sqlSession.close();
            return null;
        } else {
            int afterPoint = beforePoint + pointHistoryVO.getAmount();
            //  user point update
            userDAO.updateUserPointByUserUUID(pointHistoryVO.getUuid(), afterPoint);
            //  point history update
            pointDAO.insertPointHistory(pointHistoryVO);
            sqlSession.commit();
            sqlSession.close();

            return afterPoint;

        }

    }
}
