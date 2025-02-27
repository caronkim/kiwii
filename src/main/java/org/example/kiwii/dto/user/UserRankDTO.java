package org.example.kiwii.dto.user;

import org.example.kiwii.vo.user.UserVO;

import java.sql.Timestamp;

public class UserRankDTO {

    private String username;   // 회원 ID (유니크)
    private int totalEarnedPoints;
    private int rank;

    public UserRankDTO(String username, int totalEarnedPoints, int rank) {
        this.username = username;
        this.totalEarnedPoints = totalEarnedPoints;
        this.rank = rank;
    }
}
