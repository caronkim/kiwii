package org.example.kiwii.dao.user;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.user.UserVO;

public class UserDAO {
    private final SqlSession sqlSession;

    public UserDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public UserVO selectUserByUsername(String username) {
        UserVO userVO = null;
        try{
            userVO = sqlSession.selectOne("User.selectUserByUsername", username);
            return userVO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return userVO;
        }
    }
}
