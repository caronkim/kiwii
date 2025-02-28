package org.example.kiwii.service.user;

import org.apache.ibatis.session.SqlSession;
import org.example.kiwii.dao.point.PointDAO;
import org.example.kiwii.dao.user.UserDAO;
import org.example.kiwii.dto.user.UserInfoDTO;
import org.example.kiwii.dto.user.UserRankDTO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;
import org.example.kiwii.vo.point.PointHistoryVO;
import org.example.kiwii.vo.user.UserVO;

import java.util.ArrayList;
import java.util.List;

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

    public UserInfoDTO selectUserByUserUUID(int uuid) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserInfoDTO selectedUserWithRank= userDAO.selectUserByUserUUID(uuid);


        if(selectedUserWithRank == null){
            //username 유저가 존재하지 않을 경우
            sqlSession.close();
            return null;
        }else {
            sqlSession.close();
            return selectedUserWithRank;
        }

    }

    public Integer depositPointByUserUUID(PointHistoryVO pointHistoryVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);
        PointDAO pointDAO = new PointDAO(sqlSession);

        try {
            Integer beforePoint = userDAO.selectUserPointByUserUUID(pointHistoryVO.getUuid());
            Integer beforeTotalEarnedPoint = userDAO.selectUserTotalEarnedPointByUserUUID(pointHistoryVO.getUuid());



            if (beforePoint == null || beforeTotalEarnedPoint == null) {
                return null;  // 사용자 정보 없음
            }

            int afterPoint = beforePoint + pointHistoryVO.getAmount();
            int afterTotalEarnedPoint = beforeTotalEarnedPoint + pointHistoryVO.getAmount();

            // ✅ user point update
            userDAO.updateUserPointByUserUUID(pointHistoryVO.getUuid(), afterPoint);

            // ✅ user Total earned point update
            userDAO.updateUserTotalEarnedPointByUserUUID(pointHistoryVO.getUuid(), afterTotalEarnedPoint);

            // ✅ point history insert
            pointDAO.insertPointHistory(pointHistoryVO);
            sqlSession.commit(); // ✅ 커밋

            return afterPoint;
        } catch (Exception e) {
            sqlSession.rollback(); // ✅ 예외 발생 시 롤백
            throw new RuntimeException("포인트 적립 중 오류 발생: " + e.getMessage(), e);
        } finally {
            sqlSession.close(); // ✅ 중복 close 제거
        }
    }


    public Integer usePointByUserUUID(PointHistoryVO pointHistoryVO) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);
        PointDAO pointDAO = new PointDAO(sqlSession);

        try {
            Integer beforePoint = userDAO.selectUserPointByUserUUID(pointHistoryVO.getUuid());

            if (beforePoint == null) {
                return null;  // 사용자 정보 없음
            }

            int afterPoint = beforePoint + pointHistoryVO.getAmount();

            if (afterPoint < 0) {
                return afterPoint;  // 포인트 부족
            }

            userDAO.updateUserPointByUserUUID(pointHistoryVO.getUuid(), afterPoint);
            pointDAO.insertPointHistory(pointHistoryVO);
            sqlSession.commit();

            return afterPoint;
        } catch (Exception e) {
            sqlSession.rollback(); // ✅ 예외 발생 시 롤백
            throw new RuntimeException("포인트 사용 중 오류 발생: " + e.getMessage(), e);
        } finally {
            sqlSession.close(); // ✅ 중복 close() 제거
        }
    }

    public UserVO insertUser(UserVO registerTryUser) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        try {
            UserVO registeredUser = userDAO.insertUser(registerTryUser);
            sqlSession.commit();
            return registeredUser;


        } catch (Exception e){
            sqlSession.rollback(); // ✅ 예외 발생 시 롤백
            throw new RuntimeException("유저 생성 중 오류 발생: " + e.getMessage(), e);
        } finally {
            sqlSession.close(); // ✅ 중복 close() 제거
        }
    }

    public UserVO selectUserByUsername(String enteredUsername) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser = userDAO.selectUserByUsername(enteredUsername);

        if(selectedUser == null){
            sqlSession.close();
            return null;
        } else {
            sqlSession.close();
            return selectedUser;
        }

    }

    public UserVO selectUserByAccountId(String enteredAccountId) {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);

        UserVO selectedUser = userDAO.selectUserByAccountId(enteredAccountId);

        if(selectedUser == null){
            sqlSession.close();
            return null;
        } else {
            sqlSession.close();
            return selectedUser;
        }
    }

    public List<UserRankDTO> selectUserByRank() {
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);
        List<UserVO> rankedUser = userDAO.selectUserByRank();
        if (rankedUser == null) {
            sqlSession.close();
            return null;
        } else {
            sqlSession.close();
            List<UserRankDTO> userRankDTOList = new ArrayList<UserRankDTO>();
            int rank = 1;
            for (UserVO userVO : rankedUser) {
                if(rank > 11)
                    break;
                UserRankDTO userDTO = new UserRankDTO(
                        userVO.getUsername(),
                        userVO.getTotalEarnedPoints(),
                        rank
                );
                userRankDTOList.add(userDTO);
                rank++;
            }
            return userRankDTOList;
        }

    }
}
