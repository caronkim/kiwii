package org.example.kiwii.dao.user;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.vo.user.UserVO;

import java.util.HashMap;
import java.util.Map;

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

    public UserVO selectUserByUserUUID(int uuid) {
        UserVO userVO = null;
        try {
            userVO = sqlSession.selectOne("User.selectUserByUserUUID", uuid);
            return userVO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return userVO;
        }
    }

    public Integer selectUserPointByUserUUID(int uuid) {
        try{
            return sqlSession.selectOne("User.selectUserPointByUserUUID", uuid);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void updateUserPointByUserUUID(int uuid, int afterPoint) {
        Map<String, Object> params = new HashMap<>();

        params.put("afterPoint", afterPoint);
        params.put("uuid", uuid);

        try{
            sqlSession.update("User.updateUserPointByUserUUID", params);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
