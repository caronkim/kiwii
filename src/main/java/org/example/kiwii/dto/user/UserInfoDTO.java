package org.example.kiwii.dto.user;

import org.example.kiwii.vo.user.UserVO;

import java.sql.Timestamp;

public class UserInfoDTO {
    private int uuid;         // 회원 고유번호 (자동 증가)
    private String username;   // 회원 ID (유니크)
    private String password;   // 비밀번호 (해싱 필요)
    private String accountId;  // 계좌번호
    private int point;     // 포인트 (기본값 0)
    private Timestamp createdAt;  // 생성일 (자동 설정)
    private Timestamp updatedAt;  // 업데이트일 (자동 설정)
    private Timestamp deletedAt;
    private int totalEarnedPoints;
    private int rank;

    public UserInfoDTO() {}

    public UserInfoDTO(UserVO user, int rank) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.accountId = user.getAccountId();
        this.point = user.getPoint();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
        this.totalEarnedPoints = user.getTotalEarnedPoints();
        this.rank = rank;
    }
}
