package org.example.kiwii.service.user;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.user.UserDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.user.UserVO;

public class UserService {

    public UserVO loginByUserVO(UserVO loginUser) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser= userDAO.selectUserByUsername(loginUser.getUsername());

        if(selectedUser == null){
            //username 유저가 존재하지 않을 경우
            return null;
        }
        if(selectedUser.getPassword().equals(loginUser.getPassword())){
            return selectedUser;
        } else {
            return null;
        }


    }

    public UserVO selectUserByUserUUID(int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser= userDAO.selectUserByUserUUID(uuid);

        if(selectedUser == null){
            //username 유저가 존재하지 않을 경우
            return null;
        }else {
            return selectedUser;
        }

    }
}
