package org.example.kiwii.vo.user;

import java.sql.Timestamp;

public class UserVO {
    private int uuid;         // 회원 고유번호 (자동 증가)
    private String username;   // 회원 ID (유니크)
    private String password;   // 비밀번호 (해싱 필요)
    private String accountId;  // 계좌번호
    private int point;     // 포인트 (기본값 0)
    private Timestamp createdAt;  // 생성일 (자동 설정)
    private Timestamp updatedAt;  // 업데이트일 (자동 설정)
    private Timestamp deletedAt;
    private int totalEarnedPoints;// 삭제일 (NULL 허용)

    public UserVO() {}

    public UserVO(int uuid, String username, String accountId) {
        this.uuid = uuid;
        this.username = username;
        this.accountId = accountId;
    }

    public UserVO(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
    public int getTotalEarnedPoints() {
        return totalEarnedPoints;
    }

    public void setTotalEarnedPoints(int totalEarnedPoints) {
        this.totalEarnedPoints = totalEarnedPoints;
    }
}

